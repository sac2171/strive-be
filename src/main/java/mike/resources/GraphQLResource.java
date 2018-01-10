package mike.resources;

import com.google.common.collect.ImmutableMap;
import graphql.ExecutionResult;
import graphql.GraphQL;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.Map;

@Path("/graphql")
@Produces(MediaType.APPLICATION_JSON)
public class GraphQLResource {

    private GraphQL graphQL;

    public GraphQLResource(GraphQL graphQL) {
        this.graphQL = graphQL;
    }

    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> execute(GraphQLRequest graphQLRequest){
        ImmutableMap.Builder<String, Object> contextBuilder = new ImmutableMap.Builder<String, Object>();
        ImmutableMap<String, Object> context = contextBuilder.build();
        ExecutionResult executionResult = graphQL.execute(graphQLRequest.getQuery());
        Map<String, Object> result = new LinkedHashMap<String, Object>();

        result.put("data", executionResult.getData());
        result.put("errors", executionResult.getErrors());
        return result;
    }

    static class GraphQLRequest{
        private String query;
        private String variables;

        String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        String getVariables() {
            return variables;
        }

        public void setVariables(String variables) {
            this.variables = variables;
        }
    }
}
