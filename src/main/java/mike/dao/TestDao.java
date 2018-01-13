package mike.dao;

import io.dropwizard.hibernate.AbstractDAO;
import mike.api.Test;
import org.hibernate.SessionFactory;

import java.util.List;

public class TestDao extends AbstractDAO<Test> {
    public TestDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Test insert(Integer userId){
        Test test = new Test(userId);
        return persist(test);
    }

    public List<Test> findAll() {
        return list(namedQuery("mike.api.Test.findAll"));
    }
}
