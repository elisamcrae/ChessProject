package Communication;

import chess.ChessGame;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinPlayerUCommand extends UserGameCommand {
    private int gameID;
    private ChessGame.TeamColor playerColor;
    public JoinPlayerUCommand(String authToken, int gameID, ChessGame.TeamColor color) {
        super(authToken);
        commandType = CommandType.JOIN_PLAYER;
        this.gameID = gameID;
        playerColor = color;
    }
    public ChessGame.TeamColor getPlayerColor() {return playerColor;}
    public int getGameID() {return gameID;}
}
