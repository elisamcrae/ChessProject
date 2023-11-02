package requests;

/**
 * HTTP request information to join a chess game
 */
public class JoinGameRequest {
    /**
     * The int which will contain the game-unique ID created by incrementing a counter
     */
    private int gameID;
    /**
     * The string which will contain the color that the player wants to be in the game. A blank field will result in the user being an observer
     */
    private String playerColor;
    /**
     * The string which will contain the user-unique authentication token
     */
    private String authToken;

    public String getPlayerColor() {
        return playerColor;
    }
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public int getGameID() {
        return gameID;
    }
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

    /**
     * Constructor to create a join game request.
     *
     * @param gameID    the string to which the game ID should be set
     * @param playerColor   the color that the player wishes to be in the game
     */
    public JoinGameRequest(int gameID, String playerColor) {
        this.playerColor = playerColor;
        this.gameID = gameID;
    }
}
