package Communication;

import webSocketMessages.userCommands.UserGameCommand;

public class JoinObserverUCommand extends UserGameCommand {
    private int gameID;
    public JoinObserverUCommand(String authToken, int gameID) {
        super(authToken);
        commandType = CommandType.JOIN_OBSERVER;
        this.gameID = gameID;
    }

    public int getGameID() {return gameID;}
}
