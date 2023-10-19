package requests;

/**
 * HTTP request information to login
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Constructor which creates a login request.
     * @param username  the string to which the username should be set
     * @param password  the string to which the password should be set
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the player attempting to log in.
     * @return  the string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the player attempting to log in.
     * @param username  the string to which the username should be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the player attempting to log in.
     * @return  the string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the player attempting to log in.
     * @param password  the string to which the password should be set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
