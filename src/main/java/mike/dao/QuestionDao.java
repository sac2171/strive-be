package mike.dao;

import io.dropwizard.hibernate.AbstractDAO;
import mike.api.Question;
import org.hibernate.SessionFactory;

import java.util.List;

public class QuestionDao extends AbstractDAO<Question> {
    public QuestionDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Question> findAll() {
        return list(namedQuery("mike.api.Question.findAll"));
    }
}
