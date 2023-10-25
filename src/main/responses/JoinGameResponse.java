package responses;

/**
 * HTTP response information when attempting to join a chess game
 */
public class JoinGameResponse {
    private String message;
    private String gameID;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the game ID for the game being joined.
     * @return  the string gameID
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Sets the current game's game ID to a new string.
     * @param gameID    the string to which the current game ID should be set
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
