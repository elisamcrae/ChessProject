package model;

import chess.ChessGameE;

import java.util.ArrayList;

/**
 * Object used for passing and collecting chess game information
 */
public class Game {
    /**
     * The int which will contain the game-specific ID
     */
    private int gameID;
    /**
     * The string which will contain the username for the white player
     */
    private String whiteUsername;
    /**
     * The string which will contain the username for the black player
     */
    private String blackUsername;
    /**
     * The string which will contain the user-created name of the game
     */
    private String gameName;
    /**
     * The chess game as a chess object
     */
    private ChessGameE game;
    /**
     * The counter used to create the game ID
     */
    static int counter = 100;
    /**
     * The array of observers in the game
     */
    private ArrayList<String> observers = new ArrayList<>();

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    /**
     * Adds a player to be an observer to the chess game.
     *
     * @param observer  the username of the player to be an observer
     */
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
     *
     * @param whiteUsername string username for white player
     * @param blackUsername string username for black player
     * @param gameName  name of the game - does not have to be unique
     */
    public Game(String whiteUsername, String blackUsername, String gameName) {
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = new ChessGameE();
        this.gameID = counter;
        counter++;
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
