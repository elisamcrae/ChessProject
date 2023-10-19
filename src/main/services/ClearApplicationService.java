package services;
//Spark.externalStaticFileLocation("path/to/web/folder");


/**
 * Removes all data from the application by calling clear on each DAO class
 */
public class ClearApplicationService {

    /**
     * Service that clears the application by removing all data from the databases.
     * The authDAO, userDAO, and gameDAOs will all be reset to wipe all the data.
     */
    void clearApplication() {
        //AuthDAO.clear();
        //UserDAO.clear();
        //GameDAO.clear();
    }

    public ClearApplicationService() {
        clearApplication();
    }
}
