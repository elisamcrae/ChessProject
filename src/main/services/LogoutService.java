package services;

import model.AuthToken;

/**
 * Logs out the user represented by the authToken.
 */
public class LogoutService {

    /**
     * Service that logs the user out by deleting the current ongoing session.
     * User is logged out only based on the authentication token.
     * @param r the authentication token of the current user logged in
     */
    public void logout(AuthToken r) {
        //Deletes current session
    }
}
