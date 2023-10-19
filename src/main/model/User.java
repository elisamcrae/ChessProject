package model;

/**
 * Object used for passing and collecting user information
 */
public class User {
    private String username;
    private String password;
    private String email;

    /**
     * Resets the username, password, and email strings by setting them all equal to blank strings.
     */
    public void reset() {
        username = "";
        password = "";
        email = "";
    }

    public User(String username, String password, String email) {
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
