package responses;

/**
 * HTTP response information when attempting to create a new game
 */
public class CreateGameResponse {
    private String message;
    private String gameName;
    private String whiteUsername;
    private String blackusername;

    /**
     * Returns the message of the createGame response.
     * @return  the string message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the createGame string message.
     * @param message   the string to which the current response message should be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the name of the game to be created.
     * @return  the string gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the gameName string.
     * @param gameName  the string to which the current game name should be set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Returns the white username for the game to be created.
     * @return  the white player's username
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * Sets the white player's username.
     * @param whiteUsername the string to which the current white username should be set
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * Returns the black username for the game to be created.
     * @return  the black player's username
     */
    public String getBlackusername() {
        return blackusername;
    }

    /**
     * Sets the black player's username.
     * @param blackusername the string to which the current black username should be set
     */
    public void setBlackusername(String blackusername) {
        this.blackusername = blackusername;
    }

    /**
     * Empty constructor to create a game response.
     */
    public CreateGameResponse() {
    }
}
