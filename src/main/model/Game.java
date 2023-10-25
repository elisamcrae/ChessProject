package model;

import chess.ChessGameE;

import java.util.ArrayList;

/**
 * Object used for passing and collecting chess game information
 */
public class Game {
    private int gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGameE game;
    static int counter = 1;
    private ArrayList<String> observers = new ArrayList<>();

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public ArrayList<String> getObservers() {
        return observers;
    }

    public void addObserver(String observer) {
        this.observers.add(observer);
    }
    /**
     * Resets the game ID, white username, black username, game name, and chess board by
     * setting them equal to -10000 or blank strings.
     */
    public void reset() {
        gameID = -10000;
        whiteUsername = "";
        blackUsername = "";
        gameName = "";
        game.getBoard().resetBoard();
    }

    /**
     * Constructor to create a new game object.
     * @param gameID    unique game number
     * @param whiteUsername string username for white player
     * @param blackUsername string username for black player
     * @param gameName  name of the game - does not have to be unique
     * @param game  chessGame game
     */
    public Game(String whiteUsername, String blackUsername, String gameName) {
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new ChessGameE();
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID() {
        this.gameID = counter++;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ChessGameE getGame() {
        return game;
    }

    public void setGame(ChessGameE game) {
        this.game = game;
    }
}
