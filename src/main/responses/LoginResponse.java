package responses;

/**
 * HTTP response information when attempting to login
 */
public class LoginResponse {
    private String message;
    private String authToken;
    private String username;

    /**
     * Returns the message for the login response.
     * @return  the string message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the login response message to a new string.
     * @param message   the string to which the current message should be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the authentication token for the player attempting to log in.
     * @return  the string authentication token
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Sets the authentication token to a new string.
     * @param authToken the string to which the current authentication token should be set
     */
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the username for the player attempting to log in.
     * @return  the string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the player attempting to log in.
     * @param username  the string to which the current username should be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Empty constructor for the login response.
     */
    public LoginResponse() {
    }

}
