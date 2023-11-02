package dataAccess;

import model.AuthToken;
import model.User;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores and retrieves user objects
 */
public interface UserDAO {
    /**
     * Database that holds all user objects
     */
    ArrayList<User> userDB = UserDAOMemory.getUserDatabase();
    /**
     * Creates a user in the database given the user object.
     *
     * @param u the user object to be stored in the database
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static void createUser(User u) throws DataAccessException {
        userDB.add(u);
    };
    /**
     * Returns the user object given the user's authentication token.
     *
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

    /**
     * Finds the user by the username and password.
     *
     * @param username  the string username of the user to be found
     * @param password  the string password of the user to be found
     * @return  the user that was found
     * @throws DataAccessException  if database cannot be located
     */
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
     *
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

    /**
     * Attempts to find user within the database.
     *
     * @param u the user to be found
     * @return  true if the user was successfully found, otherwise returns false
     */
    static boolean contains(User u) {
        for (User user : userDB) {
            if (Objects.equals(user.getUsername(), u.getUsername()) && Objects.equals(user.getPassword(), u.getPassword()) && Objects.equals(user.getEmail(), u.getEmail())) {
                return true;
            }
        }
        return userDB.contains(u);
    }

    /**
     * Gives the username that corresponds with a userID.
     *
     * @param userID    the int ID of the user for which the username is needed
     * @return  the string username
     */
    static String getUsername(int userID) {
        for (User user : userDB) {
            if (user.getUserID() == userID) {
                return user.getUsername();
            }
        }
        return null;
    }
}
