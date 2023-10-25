package responses;

/**
 * HTTP response information when attempting to create a new game
 */
public class CreateGameResponse {
    private String message;
    private String gameName;
    private Integer gameID;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

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
}
