package Communication;

import chess.ChessGame;
import chess.ChessGameE;
import webSocketMessages.serverMessages.ServerMessage;

public class LoadGameSMessage extends ServerMessage {
    public ChessGame game;
    public LoadGameSMessage(ServerMessageType type, ChessGame game) {
        super(type, game);
        this.game = game;
    }
}
