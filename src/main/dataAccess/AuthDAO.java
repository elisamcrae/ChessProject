package dataAccess;

import model.AuthToken;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores and retrieves authentication token objects
 */
public interface AuthDAO {
    /**
     * Database for storing the authentication tokens
     */
    ArrayList<AuthToken> authDB = AuthDAOMemory.getAuthDatabase();

    /**
     * Clears all the information within the database by deleting all authentication tokens.
     */
    static void clear() {
        authDB.clear();
    };

    /**
     * Puts the authentication token into the databse.
     *
     * @param u the authentication token object
     * @throws DataAccessException  if the database cannot be located
     */
    static void createAuth(AuthToken u) throws DataAccessException {
        authDB.add(u);
    };

    /**
     * Deletes an authentication token from the databse.
     *
     * @param auth  the authentication string correlating to the authentication token object to be deleted
     * @return  true if the auth string was found in the database and deleted
     * @throws DataAccessException  if the database cannot be located
     */
    static boolean delete(String auth) throws DataAccessException {
        if (authDB.isEmpty()) {
            return false;
        }
        for(int i = 0; i < authDB.size(); ++i) {
            if (Objects.equals(authDB.get(i).getAuthToken(), auth)) {
                authDB.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to find the authentication string within the database.
     *
     * @param auth  the authentication string correlating to the authentication token object in the database
     * @return  true if the auth string was found, otherwise returns false
     * @throws DataAccessException  if the database cannot be located
     */
    static boolean isFound(String auth) throws DataAccessException {
        if (authDB.isEmpty()) {
            return false;
        }
        for (AuthToken authToken : authDB) {
            if (Objects.equals(authToken.getAuthToken(), auth)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the userID correlating to an authentication token.
     *
     * @param auth  the string authentication correlating to the authentication token object in the database
     * @return  the int userID, otherwise returns -10000
     */
    static int getUserID(String auth) {
        for (AuthToken authToken : authDB) {
            if (Objects.equals(authToken.getAuthToken(), auth)) {
                return authToken.getUserID();
            }
        }
        return -10000;
    }
}
