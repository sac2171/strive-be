package strive.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import strive.api.Test;
import strive.dao.TestDao;

import java.util.Map;

public class GradeTestMutation implements DataFetcher {

    private TestDao testDao;

    public GradeTestMutation(TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public Test get(DataFetchingEnvironment env) {
        Map<String, Object> input  = env.getArgument("input");
        return testDao.grade((Integer) input.get("id"), (Integer) input.get("grade"));
    }
}
