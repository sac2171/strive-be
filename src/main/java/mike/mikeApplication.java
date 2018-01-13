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
import mike.api.Test;
import mike.api.User;
import mike.dao.AnswerDao;
import mike.dao.QuestionDao;
import mike.dao.TestDao;
import mike.dao.UserDao;
import mike.fetchers.*;
import mike.resources.GraphQLResource;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;
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
        configureCors(environment);
        configureGraphQl(environment);

    }

    private final HibernateBundle<mikeConfiguration> hibernate = new HibernateBundle<mikeConfiguration>(Question.class, Answer.class, Test.class, User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(mikeConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private void configureGraphQl(Environment environment){
        final QuestionDao questionDao = new QuestionDao(hibernate.getSessionFactory());
        final AnswerDao answerDao = new AnswerDao(hibernate.getSessionFactory());
        final UserDao userDao = new UserDao(hibernate.getSessionFactory());
        final TestDao testDao = new TestDao(hibernate.getSessionFactory());

        SchemaParser schemaParser = new SchemaParser();
        InputStream in = getClass().getClassLoader().getResourceAsStream("schema.graphqls");
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(new BufferedReader(new InputStreamReader(in))
                .lines().collect(Collectors.joining("\n")));

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("questions", new QuestionsQuery(questionDao))
                        .dataFetcher("tests", new TestsQuery(testDao))
                )
                .type("Mutation", builder -> builder
                        .dataFetcher("submitAnswer" , new SubmitAnswerMutation(answerDao))
                        .dataFetcher("addUser" , new AddUserMutation(userDao))
                        .dataFetcher("newTest" , new NewTestMutation(testDao))
                )

                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        final GraphQLResource resource = new GraphQLResource(graphQL);
        environment.jersey().register(resource);

    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

}
