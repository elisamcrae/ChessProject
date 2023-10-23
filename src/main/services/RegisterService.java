package services;

import dataAccess.DataAccessException;
import dataAccess.UserDAO;
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
    public RegisterResponse register(RegisterRequest r) throws DataAccessException {
        try {
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail());
            RegisterResponse rr = new RegisterResponse();
            if (!UserDAO.contains(u)) {
                UserDAO.createUser(u);
                rr.setMessage("200");
                rr.setUsername(r.getUsername());
                rr.setPassword(r.getPassword());
                rr.setEmail(r.getEmail());
                return rr;
            }
            else {
                rr.setMessage("400");
                return rr;
            }
        }
        catch(DataAccessException e) {
            //Bad request
            RegisterResponse rr = new RegisterResponse();
            rr.setMessage("500");
            return rr;
        }
    }

    public RegisterService() {
    }
}
