package model;

/**
 * Object used for passing and collecting user information
 */
public class User {
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
     * The int which will be the user-specific ID
     */
    private int userID;
    /**
     * The counter used to generate userIDs by incrementation
     */
    static private int count = 0;

    /**
     * Resets the username, password, and email strings by setting them all equal to blank strings.
     */
    public void reset() {
        username = "";
        password = "";
        email = "";
    }

    /**
     * Constructor to create a user
     *
     * @param username  the username of the player
     * @param password  the password of the player
     * @param email the email address of the player
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = ++count;
    }

    public User(String username, String password, String email, int userID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userID = userID;
        ++count;
    }

    public int getUserID() {
        return userID;
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
