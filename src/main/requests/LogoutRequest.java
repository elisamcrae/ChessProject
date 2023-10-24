package requests;

public class LogoutRequest {
    private String authorization;

    public String getAuthToken() {
        return authorization;
    }

    public void setAuthToken(String authToken) {
        this.authorization = authToken;
    }
}
