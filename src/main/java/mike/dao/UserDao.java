package mike.dao;

import io.dropwizard.hibernate.AbstractDAO;
import mike.api.User;
import org.hibernate.SessionFactory;

public class UserDao extends AbstractDAO<User> {
    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User insert(String firstName, String lastName, String email){
        User user = new User(firstName, lastName, email);
        return persist(user);
    }
}
