package mike.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import mike.api.Question;
import mike.dao.QuestionDao;

import java.util.List;

public class QuestionsQuery implements DataFetcher {

    private QuestionDao questionDao;

    public QuestionsQuery(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> get(DataFetchingEnvironment environment) {
        return questionDao.findAll();
    }
}
