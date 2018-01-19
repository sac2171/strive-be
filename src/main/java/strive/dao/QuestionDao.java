package strive.dao;

import io.dropwizard.hibernate.AbstractDAO;
import strive.api.Question;
import org.hibernate.SessionFactory;

import java.util.List;

public class QuestionDao extends AbstractDAO<Question> {
    public QuestionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Question> findAll() {
        return list(namedQuery("strive.api.Question.findAll"));
    }
}
