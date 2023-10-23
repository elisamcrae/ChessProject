package dataAccess;

import model.AuthToken;
import model.User;

import java.util.ArrayList;

/**
 * Stores and retrieves user objects
 */
public interface UserDAO {
    ArrayList<User> userDB = new ArrayList<>();
    /**
     * Creates a user in the database given the user object
     * @param u the user object to be stored in the database
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static void createUser(User u) throws DataAccessException {
        userDB.add(u);
    };
    /**
     * Returns the user object given the user's authentication token.
     * @param a the authentication token for the user
     * @return  the user object correlating to the authentication token given
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    User getUser(AuthToken a) throws DataAccessException;
    /**
     * Deletes the user from the database.
     * @param u the user object to be deleted
     * @throws DataAccessException exception thrown if the database cannot be accessed properly
     */
    void deleteUser(User u) throws DataAccessException;
    /**
     * Clears all the information within the database by deleting all users.
     */
    static void clear() {
        userDB.clear();
    };
    static boolean contains(User u) {
        if (userDB.contains(u)) {
            return true;
        }
        return false;
    }
}
