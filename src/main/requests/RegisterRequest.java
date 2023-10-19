package requests;

/**
 * HTTP request information to register a new user into the database
 */
public class RegisterRequest {
    private String username;
    private String password;
    private String email;

    /**
     * Constructor to create a register request.
     * @param username  the string to which the username should be set
     * @param password  the string to which the password should be set
     * @param email the string to which the email should be set
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the username for the player attempting to register.
     * @return  the string username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the player attempting to register.
     * @param username  the string to which the username should be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password for the player attempting to register.
     * @return  the string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the player attempting to register.
     * @param password  the string to which the password should be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email for the player attempting to register.
     * @return  the string email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for the player attempting to register.
     * @param email the string to which the email should be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
