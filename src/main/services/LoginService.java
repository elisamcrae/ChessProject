package services;

import requests.LoginRequest;
import responses.LoginResponse;

/**
 * Logs in an existing user and returns a new authToken.
 */
public class LoginService {

//    public LoginService(LoginRequest r) {
//        login(r);
//    }

    /**
     * Service that responds to an HTTP request to log in a user.
     * Calls find user to check if the user is in the system.
     * Then inserts the authentication token to start a session.
     * @param r the HTTP request for a user login
     * @return  the HTTP response to log in the user
     */
    public LoginResponse login(LoginRequest r) {
        //findUser with username;
        //insertAuth with the authtoken;
        //return LoginResponse r;
        return null;
    }
}
