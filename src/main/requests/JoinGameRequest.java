package requests;

/**
 * HTTP request information to join a chess game
 */
public class JoinGameRequest {
    private int gameID;
    private String playerColor;
    private String authToken;

    public String getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the game ID for the current chess game.
     * @return gameID
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Sets the current game ID to the string parameter.
     * @param gameID    the string to which the current chess game's ID should be set
     */
    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Constructor to create a join game request.
     * @param gameID    the string to which the game ID should be set
     */
    public JoinGameRequest(int gameID) {
        this.gameID = gameID;
    }
    public JoinGameRequest(int gameID, String playerColor) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

}
