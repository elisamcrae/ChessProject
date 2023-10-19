package responses;

/**
 * HTTP response information when attempting to register a new user
 */
public class RegisterResponse {
    private String message;
    private String username;
    private String password;
    private String email;

    public RegisterResponse() {
    }

    /**
     * Returns the message for the register response.
     * @return  the string message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message to a new string.
     * @param message   the string to which the current message should be set
     */
    public void setMessage(String message) {
        this.message = message;
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
     * @param username  the string to which the current username should be set
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
     * @param password  the string to which the current password should be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the email for the player attemtping to register.
     * @return  the string email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for the player attempting to register.
     * @param email the string email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
