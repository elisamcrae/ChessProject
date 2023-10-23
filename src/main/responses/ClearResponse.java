package responses;

public class ClearResponse {
    private String message;
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
}
