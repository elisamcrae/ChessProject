package responses;
/**
 * HTTP response information when attempting to log out
 */
public class LogoutResponse {
    /**
     * The string which will contain the response message, corresponding to the response status code
     */
    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
