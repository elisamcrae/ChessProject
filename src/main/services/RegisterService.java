package services;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import dataAccess.UserSQL;
import model.AuthToken;
import model.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

/**
 * Register a new user by collecting username, password, and email.
 */
public class RegisterService {

    /**
     * Service that responds to an HTTP request to add a new user into the database.
     * Checks that the user is not already an existing user.
     * If all fields match a current user, an error will be thrown.
     *
     * @param r the HTTP request to register a user
     * @return  the HTTP response to the register user request
     */
    public RegisterResponse register(RegisterRequest r) {
        RegisterResponse rr = new RegisterResponse();
        try {
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail());
            if (!UserSQL.contains(u)) {
                UserSQL.createUser(u);

                rr.setMessage("Success!");
                rr.setUsername(r.getUsername());
                rr.setPassword(r.getPassword());
                rr.setEmail(r.getEmail());

                AuthToken a = new AuthToken(u.getUserID());
                AuthSQL.createAuth(a);
                rr.setAuth(a.getAuthToken());
                return rr;
            }
            else {
                rr.setMessage("Error: already taken");
                return rr;
            }
        }
        catch(DataAccessException e) {
            rr.setMessage("Error: cannot complete request");
            return rr;
        }
    }
}
