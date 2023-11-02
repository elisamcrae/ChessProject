package requests;

/**
 * HTTP request information to create a new chess game
 */
public class CreateGameRequest {
    /**
     * The string which will contain the user-created name of the game
     */
    private String gameName;
    /**
     * The string which will contain the username of the white player
     */
    private String whiteUsername;
    /**
     * The string which will contain the username of the black player
     */
    private String blackUsername;
    /**
     * The string which will contain the user-unique authentication token
     */
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public String getWhiteUsername() {
        return whiteUsername;
    }
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * HTTP request to create new chess game.
     *
     * @param gameName  the name to which the new chess game should be set
     * @param whiteUsername the username for the white player
     * @param blackUsername the username for the black player
     */
    public CreateGameRequest(String gameName, String whiteUsername, String blackUsername) {
        this.gameName = gameName;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
    }
}
