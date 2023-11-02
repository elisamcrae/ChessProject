package services;
import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import dataAccess.GameSQL;
import dataAccess.UserSQL;

/**
 * Removes all data from the application by calling clear on each DAO class
 */
public class ClearApplicationService {

    /**
     * Service that clears the application by removing all data from the databases.
     * The AuthSQL, UserSQL, and GameSQLs will all be reset to wipe all the data.
     */
    public void clearApplication() {
        try {
            AuthSQL.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            UserSQL.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            GameSQL.clear();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
