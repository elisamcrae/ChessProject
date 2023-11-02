package responses;
/**
 * HTTP response information when attempting to register a new user
 */
public class RegisterResponse {
    /**
     * The string which will contain the response message, corresponding to the response status code
     */
    private String message;
    /**
     * The string which will contain the user-created username
     */
    private String username;
    /**
     * The string which will contain the user-created password
     */
    private String password;
    /**
     * The string which will store the user-created email
     */
    private String email;
    /**
     * The string which will contain the user-unique authentication token
     */
    private String authToken;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAuth(String auth) {
        this.authToken = auth;
    }
}
