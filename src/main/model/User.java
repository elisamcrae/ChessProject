package model;

public class User {
    private String username;
    private String password;
    private String email;

    public void reset() {
        username = "";
        password = "";
        email = "";
    }
}
