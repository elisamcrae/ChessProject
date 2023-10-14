package requests;

public class JoinGameRequest {
    private String gameID;

    /**
     * Returns the game ID for the current chess game
     * @return gameID
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Sets the current game ID to the string parameter
     * @param gameID    the string to which the current chess game's ID should be set
     */
    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public JoinGameRequest(String gameID) {
        this.gameID = gameID;
    }
}
