package Communication;

import chess.ChessMove;
import webSocketMessages.userCommands.UserGameCommand;

public class MakeMoveUCommand extends UserGameCommand {
    private int gameID;
    private ChessMove move;
    public MakeMoveUCommand(String authToken, int gameID, ChessMove m) {
        super(authToken);
        commandType = CommandType.MAKE_MOVE;
        this.gameID = gameID;
        move = m;
    }
}
