package strive.dao;

import io.dropwizard.hibernate.AbstractDAO;
import strive.api.Answer;

import org.hibernate.SessionFactory;


public class AnswerDao extends AbstractDAO<Answer> {
    public AnswerDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Answer insert(long questionId, long examId, String answerText) {
        Answer answer = new Answer(questionId, examId, answerText);
        return persist(answer);
    }
}