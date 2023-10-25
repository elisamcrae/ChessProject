package services;


import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;

/**
 * Removes all data from the application by calling clear on each DAO class
 */
public class ClearApplicationService {

    /**
     * Service that clears the application by removing all data from the databases.
     * The authDAO, userDAO, and gameDAOs will all be reset to wipe all the data.
     */
    public void clearApplication() {
        AuthDAO.clear();
        UserDAO.clear();
        GameDAO.clear();
    }
}
