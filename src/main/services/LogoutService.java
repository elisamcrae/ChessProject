package services;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import requests.LogoutRequest;
import responses.LogoutResponse;

import java.sql.SQLException;

/**
 * Logs out the user represented by the authToken.
 */
public class LogoutService {

    /**
     * Service that logs the user out by deleting the current ongoing session.
     * User is logged out only based on the authentication token.
     * If the authentication error is not currently in the AuthSQL database, an error will be thrown.
     *
     * @param req the logout request which contains the authentication token of the current user logged in
     * @return the response to the logout request, which will contain the message correlated to the response status
     */
    public LogoutResponse logout(LogoutRequest req) {
        LogoutResponse r = new LogoutResponse();
        try {
            if (!AuthSQL.isFound(req.getAuthToken())) {
                r.setMessage("Error: unauthorized");
                return r;
            }
            boolean didItWork = AuthSQL.delete(req.getAuthToken());
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
        } catch (SQLException e) {
            r.setMessage("Error: unauthorized");
            return r;
        }
    }
}
