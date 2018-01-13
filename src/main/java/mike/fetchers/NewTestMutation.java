package mike.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import mike.dao.TestDao;

public class NewTestMutation implements DataFetcher {

    private TestDao testDao;


    public NewTestMutation(TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public Object get(DataFetchingEnvironment env) {
        Integer userId = env.getArgument("userId");
        return testDao.insert(userId);
    }
}
