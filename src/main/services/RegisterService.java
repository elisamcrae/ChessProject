package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.AuthToken;
import model.User;
import requests.RegisterRequest;
import responses.RegisterResponse;

/**
 * Register a new user.
 */
public class RegisterService {

    /**
     * Service that responds to an HTTP request to add a new user into the database.
     * @param r the HTTP request to register a user
     * @return  the HTTP response to the register user request
     */
    public RegisterResponse register(RegisterRequest r) {
        RegisterResponse rr = new RegisterResponse();
        try {
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail());
            if (!UserDAO.contains(u)) {
                UserDAO.createUser(u);

                rr.setMessage("Success!");
                rr.setUsername(r.getUsername());
                rr.setPassword(r.getPassword());
                rr.setEmail(r.getEmail());

                AuthToken a = new AuthToken(u.getUserID());
                AuthDAO.createAuth(a);
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
