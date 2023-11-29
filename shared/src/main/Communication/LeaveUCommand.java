package Communication;

import webSocketMessages.userCommands.UserGameCommand;

public class LeaveUCommand extends UserGameCommand {
    private int gameID;
    public LeaveUCommand(String authToken, int gameID) {
        super(authToken);
        commandType = CommandType.LEAVE;
        this.gameID = gameID;
    }
}
