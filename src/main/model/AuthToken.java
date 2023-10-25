package model;

import java.util.UUID;

/**
 * Object used for passing and collecting authentication token information
 */
public class AuthToken {
    private String authToken;
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

    public AuthToken(int userID) {
        this.userID = userID;
        this.authToken = UUID.randomUUID().toString();
    }
}
