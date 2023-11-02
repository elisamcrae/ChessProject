package responses;

/**
 * HTTP response information when attempting to log in
 */
public class LoginResponse {
    /**
     * The string which will contain the response message, corresponding to the response status code
     */
    private String message;
    /**
     * The string which will contain the user-unique authentication token
     */
    private String authToken;
    /**
     * The string which will contain the user-created username
     */
    private String username;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getAuthToken() {
        return authToken;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
