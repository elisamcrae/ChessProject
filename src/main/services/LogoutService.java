package services;

import model.AuthToken;

public class LogoutService {

    /**
     * Logs the user out by deleting the current ongoing session
     * User is logged out only based on the authentication token
     * @param r the authentication token of the current user logged in
     */
    void logout(AuthToken r) {
        //Deletes current session
    }
}
