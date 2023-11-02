package requests;

/**
 * HTTP request information to register a new user into the database
 */
public class RegisterRequest {
    /**
     * The string which will contain the user-created username
     */
    private String username;
    /**
     * The string which will contain the user-created password
     */
    private String password;
    /**
     * The string which will contain the user-created email
     */
    private String email;

    /**
     * Constructor to create a register request.
     *
     * @param username  the string to which the username should be set
     * @param password  the string to which the password should be set
     * @param email the string to which the email should be set
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
}
