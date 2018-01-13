package mike.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import mike.api.Test;
import mike.dao.TestDao;

import java.util.List;

public class TestsQuery implements DataFetcher {

    private TestDao testDao;

    public TestsQuery(TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public List<Test> get(DataFetchingEnvironment environment) {
        return testDao.findAll();
    }
}

