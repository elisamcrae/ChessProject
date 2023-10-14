package requests;

public class CreateGameRequest {
    private String gameName;
    private String whiteUsername;
    private String blackUsername;

    /**
     * Returns the name of the current game
     * @return gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Sets the name of the current game to the string parameter
     * @param gameName  the string to which the current game name should be set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * Returns the username for the white player
     * @return white player username
     */
    public String getWhiteUsername() {
        return whiteUsername;
    }

    /**
     * Sets the current white player username to the string parameter
     * @param whiteUsername the string to which the current white username should be set
     */
    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    /**
     * Returns the username for the black player
     * @return  black player username
     */
    public String getBlackUsername() {
        return blackUsername;
    }

    /**
     * Sets the current black player username to the string parameter
     * @param blackUsername the string to which the current black username should be set
     */
    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    /**
     * HTTP request to create new chess game
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
