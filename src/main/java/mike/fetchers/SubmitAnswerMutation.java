package mike.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import mike.api.Answer;
import mike.dao.AnswerDao;

import java.util.Map;

public class SubmitAnswerMutation implements DataFetcher {
    private AnswerDao answerDao;

    public SubmitAnswerMutation(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @Override
    public Answer get(DataFetchingEnvironment env) {
        Map<String, Object> input  = env.getArgument("input");
        return answerDao.insert((Integer) input.get("questionId"), (Integer) input.get("examId"), (String) input.get("response"));
    }
}
