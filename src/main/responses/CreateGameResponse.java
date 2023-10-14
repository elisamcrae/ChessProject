package responses;

public class CreateGameResponse {
    private String message;
    private String gameName;
    private String whiteUsername;
    private String blackusername;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackusername() {
        return blackusername;
    }

    public void setBlackusername(String blackusername) {
        this.blackusername = blackusername;
    }

    public CreateGameResponse() {
    }
}
