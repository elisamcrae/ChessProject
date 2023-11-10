package requests;

/**
 * HTTP request information to login
 */
public class LoginRequest {
    /**
     * The string which will contain the user-created username
     */
    private String username;
    /**
     * The string which will contain the user-created password
     */
    private String password;

    /**
     * Constructor which creates a login request.
     *
     * @param username  the string to which the username should be set
     * @param password  the string to which the password should be set
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
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
}
