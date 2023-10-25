package dataAccess;

import model.AuthToken;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores and retrieves authentication token objects
 */
public interface AuthDAO {
    ArrayList<AuthToken> authDB = AuthDAOMemory.getAuthDatabase();

    /**
     * Clears all the information within the database by deleting all authentication tokens.
     */
    static void clear() {
        authDB.clear();
    };
    static void createAuth(AuthToken u) throws DataAccessException {
        authDB.add(u);
    };

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

    static int getUserID(String auth) {
        for (AuthToken authToken : authDB) {
            if (Objects.equals(authToken.getAuthToken(), auth)) {
                return authToken.getUserID();
            }
        }
        return -10000;
    }
}
