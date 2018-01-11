package mike;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import mike.api.Answer;
import mike.api.Question;
import mike.dao.AnswerDao;
import mike.dao.QuestionDao;
import mike.fetchers.SubmitAnswerMutation;
import mike.fetchers.QuestionsFetcher;
import mike.resources.GraphQLResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

public class mikeApplication extends Application<mikeConfiguration> {

    public static void main(final String[] args) throws Exception {
        new mikeApplication().run(args);
    }

    @Override
    public String getName() {
        return "mike";
    }

    @Override
    public void initialize(final Bootstrap<mikeConfiguration> bootstrap) {

        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new MigrationsBundle<mikeConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(mikeConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(final mikeConfiguration configuration,
                    final Environment environment) {

        final QuestionDao questionDao = new QuestionDao(hibernate.getSessionFactory());
        final AnswerDao answerDao = new AnswerDao(hibernate.getSessionFactory());

        SchemaParser schemaParser = new SchemaParser();
        InputStream in = getClass().getClassLoader().getResourceAsStream("schema.graphqls");
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n")));

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("questions", new QuestionsFetcher(questionDao)))
                .type("Mutation", builder -> builder
                        .dataFetcher("submitAnswer" , new SubmitAnswerMutation(answerDao)))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        final GraphQLResource resource = new GraphQLResource(graphQL);
        environment.jersey().register(resource);
    }

    private final HibernateBundle<mikeConfiguration> hibernate = new HibernateBundle<mikeConfiguration>(Question.class, Answer.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(mikeConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

}
