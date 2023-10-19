package services;

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
        try {
            User u = new User(r.getUsername(), r.getPassword(), r.getEmail());
            //Add to UserDAO
            RegisterResponse rr = new RegisterResponse();
            rr.setMessage("200");
            return rr;
        }
        catch {
            //Bad request
            RegisterResponse rr = new RegisterResponse();
            rr.setMessage("400");
            return rr;
        }
        //also check if already taken or 500 error


        return null;
    }

    public RegisterService() {
    }
}
