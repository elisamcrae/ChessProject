package requests;

/**
 * HTTP request information to join a chess game
 */
public class JoinGameRequest {
    private String gameID;

    /**
     * Returns the game ID for the current chess game.
     * @return gameID
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Sets the current game ID to the string parameter.
     * @param gameID    the string to which the current chess game's ID should be set
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    /**
     * Constructor to create a join game request.
     * @param gameID    the string to which the game ID should be set
     */
    public JoinGameRequest(String gameID) {
        this.gameID = gameID;
    }
}
