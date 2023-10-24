package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;
import requests.LoginRequest;
import responses.LoginResponse;
import responses.RegisterResponse;

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
        //findUser with username;
        LoginResponse rr = new LoginResponse();
        try {
            User u = UserDAO.getUserByUsername(r.getUsername());
            if (u != null) {
                //insertAuth with the authtoken;
                AuthToken a = new AuthToken(u.getUserID());
                AuthDAO.createAuth(a);
                //return LoginResponse r;
                rr.setMessage("Success!");
                rr.setAuthToken(a.getAuthToken());
                rr.setUsername(r.getUsername());
                return rr;
            }
        } catch (DataAccessException e) {
            rr.setMessage("Error");
            return rr;
        }
        return null;
    }
}
