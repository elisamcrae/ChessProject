package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import requests.LogoutRequest;
import responses.LogoutResponse;

/**
 * Logs out the user represented by the authToken.
 */
public class LogoutService {

    /**
     * Service that logs the user out by deleting the current ongoing session.
     * User is logged out only based on the authentication token.
     * @param req the logout request which contains the authentication token of the current user logged in
     */
    public LogoutResponse logout(LogoutRequest req) {
        LogoutResponse r = new LogoutResponse();
        try {
            if (!AuthDAO.isFound(req.getAuthToken())) {
                r.setMessage("Error: unauthorized");
                return r;
            }
            boolean didItWork = AuthDAO.delete(req.getAuthToken());
            if (didItWork) {
                r.setMessage("Success!");
            }
            else {
                r.setMessage("Error: unauthorized");
            }
            return r;
        }
        catch (DataAccessException e) {
            r.setMessage("Error: Cannot access data");
            return r;
        }
    }
}
