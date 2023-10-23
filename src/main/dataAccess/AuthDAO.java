package dataAccess;

import model.AuthToken;

import java.util.ArrayList;

/**
 * Stores and retrieves authentication token objects
 */
public interface AuthDAO {
    ArrayList<AuthToken> authDB = new ArrayList<>();
    /**
     * Updates the authentication token by matching the information to the
     * parameter authentication token object.
     * @param a the authentication object with the updated information to be added to the database
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    void updateAuthToken(AuthToken a) throws DataAccessException;
    /**
     * Deletes the authentication token from the database.
     * @param a the authentication token object to be deleted
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    void deleteAuthToken(AuthToken a) throws DataAccessException;
    /**
     * Clears all the information within the database by deleting all authentication tokens.
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static void clear() {
        authDB.clear();
    };
}
