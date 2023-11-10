package requests;

/**
 * HTTP request information to log out
 */
public class LogoutRequest {
    /**
     * The string which will contain the user-unique authentication token
     */
    private String authorization;
    public String getAuthToken() {
        return authorization;
    }
    public void setAuthToken(String authToken) {
        this.authorization = authToken;
    }
}
