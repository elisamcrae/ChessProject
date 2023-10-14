package model;

import chess.ChessGameE;

public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGameE game;

    public void reset() {
        gameID = -10000;
        whiteUsername = "";
        blackUsername = "";
        gameName = "";
        game.getBoard().resetBoard();
    }
}
