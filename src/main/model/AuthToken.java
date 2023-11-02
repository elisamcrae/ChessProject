package model;

import java.util.UUID;

/**
 * Object used for passing and collecting authentication token information
 */
public class AuthToken {
    /**
     * The string which will contain the user-unique authentication token
     */
    private String authToken;
    /**
     * The int which will contain the user-unique ID
     */
    private int userID;

    /**
     * Resets the authentication token and user ID by setting them equal to
     * a blank string and -10000, respectively.
     */
    public void reset() {
        authToken = "";
        userID = -10000;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getUserID() {
        return userID;
    }

    /**
     * Constructor to create an authentication token object.
     * This will create the authentication token by generating a random string.
     *
     * @param userID    the int to contain a user-specific ID
     */
    public AuthToken(int userID) {
        this.userID = userID;
        this.authToken = UUID.randomUUID().toString();
    }
}
