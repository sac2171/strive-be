package mike.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import mike.api.Question;
import mike.dao.QuestionDao;

import java.util.List;

public class QuestionsFetcher implements DataFetcher {

    private QuestionDao questionDao;

    public QuestionsFetcher(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> get(DataFetchingEnvironment environment) {
        return questionDao.findAll();
    }
}
