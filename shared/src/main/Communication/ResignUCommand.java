package Communication;

import webSocketMessages.userCommands.UserGameCommand;

public class ResignUCommand extends UserGameCommand {
    private int gameID;
    public ResignUCommand(String authToken, int gameID) {
        super(authToken);
        commandType = CommandType.RESIGN;
        this.gameID = gameID;
    }

    public int getGameID() {return gameID;}
}
