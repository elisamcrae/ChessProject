package dataAccess;

import model.AuthToken;
import model.User;

import java.util.ArrayList;
import java.util.Objects;

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
    static User getUser(AuthToken a) throws DataAccessException {
        for(int i = 0; i < userDB.size(); ++i) {
            if (userDB.get(i).getUserID() == a.getUserID()) {
                return userDB.get(i);
            }
        }
        return null;
    }

    static User getUserByUsername(String username, String password) throws DataAccessException {
        for(int i = 0; i < userDB.size(); ++i) {
            if (Objects.equals(userDB.get(i).getUsername(), username) && Objects.equals(userDB.get(i).getPassword(), password)) {
                return userDB.get(i);
            }
        }
        return null;
    }
    /**
     * Deletes the user from the database.
     * @param u the user object to be deleted
     * @throws DataAccessException exception thrown if the database cannot be accessed properly
     */
    static void deleteUser(User u) throws DataAccessException {
        userDB.remove(u);
    }

    /**
     * Clears all the information within the database by deleting all users.
     */
    static void clear() {
        userDB.clear();
    };
    static boolean contains(User u) {
        for(int i = 0; i < userDB.size(); ++i) {
            if (Objects.equals(userDB.get(i).getUsername(), u.getUsername()) && Objects.equals(userDB.get(i).getPassword(), u.getPassword()) && Objects.equals(userDB.get(i).getEmail(), u.getEmail())) {
                return true;
            }
        }
        if (userDB.contains(u)) {
            return true;
        }
        return false;
    }
}
