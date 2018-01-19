package strive.dao;

import io.dropwizard.hibernate.AbstractDAO;
import strive.api.Test;
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

    public Test grade(Integer testId, Integer grade){
        Test test = get(testId.longValue());
        test.setGrade(grade);
        return persist(test);
    }

    public List<Test> findAll() {
        return list(namedQuery("strive.api.Test.findAll"));
    }
}
