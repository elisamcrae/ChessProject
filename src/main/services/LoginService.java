package services;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import dataAccess.UserSQL;
import model.AuthToken;
import model.User;
import requests.LoginRequest;
import responses.LoginResponse;

/**
 * Logs in an existing user and returns a new authToken.
 */
public class LoginService {
    /**
     * Service that responds to an HTTP request to log in a user.
     * Calls find user to check if the user is in the system.
     * Then inserts the authentication token to start a session.
     *
     * @param r the HTTP request for a user login
     * @return the HTTP response to log in the user
     */
    public LoginResponse login(LoginRequest r) {
        LoginResponse rr = new LoginResponse();
        try {
            User u = UserSQL.getUserByUsername(r.getUsername(), r.getPassword());
            if (u != null) {
                AuthToken a = new AuthToken(u.getUserID());
                AuthSQL.createAuth(a);

                rr.setMessage("Success!");
                rr.setAuthToken(a.getAuthToken());
                rr.setUsername(r.getUsername());
                return rr;
            }
            else {
                rr.setMessage("Error: unauthorized");
                return rr;
            }
        } catch (DataAccessException e) {
            rr.setMessage("Error: unauthorized");
            return rr;
        }
    }
}
