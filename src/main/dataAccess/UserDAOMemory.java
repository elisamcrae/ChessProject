package dataAccess;

import model.User;

import java.util.ArrayList;

/**
 * Implementation of UserDAO when data is stored in memory
 */
public class UserDAOMemory implements UserDAO{
    /**
     * Database that holds all users
     */
    private static ArrayList<User> userDatabase = new ArrayList<>();
    public static ArrayList<User> getUserDatabase() {
        return userDatabase;
    }
}
