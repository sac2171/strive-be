package strive.fetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import strive.api.User;
import strive.dao.UserDao;

import java.util.Map;

public class AddUserMutation implements DataFetcher {
    private UserDao userDao;

    public AddUserMutation(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User get(DataFetchingEnvironment env) {
        Map<String, Object> input = env.getArgument("input");
        return userDao.insert((String) input.get("firstName"), (String) input.get("lastName"), (String) input.get("email"));
    }
}
