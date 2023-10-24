package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.util.ArrayList;

/**
 * Implementation of UserDAO when data is stored in memory
 */
public class UserDAOMemory implements UserDAO{
    private static ArrayList<User> userDatabase = new ArrayList<>();

//    @Override
//    public User getUser(AuthToken a) throws DataAccessException {
////        for (int i = 0; i < userDatabase.size(); ++i) {
////            if (userDatabase.get(i).get)
////        }
//        return null;
//    }

//    @Override
//    public void deleteUser(User u) throws DataAccessException {
//
//    }
}
