package responses;

/**
 * HTTP response information when attempting to clear all data
 */
public class ClearResponse {
    /**
     * The string which will contain the response message, corresponding to the response status code
     */
    private String message;
    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }
}
