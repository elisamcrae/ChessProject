package dataAccess;

import chess.ChessGameE;
import model.Game;
import model.User;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores and retrieves game objects
 */
public interface GameDAO {
    ArrayList<Game> gameDB = new ArrayList<>();
    /**
     * Creates a game in the database given the game object
     * @param g the game object to be stored in the database
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static boolean createGame(Game g, String auth) throws DataAccessException {
        if (AuthDAO.isFound(auth)) {
            gameDB.add(g);
            return true;
        }
        return false;
    }

    static boolean isFound(int gameID) {
        for(int i = 0; i < gameDB.size(); ++i) {
            if (gameDB.get(i).getGameID() == gameID) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a game object that correlates to the parameter game ID.
     * @param gameID    the string ID correlating to the game object
     * @return  the game object having the same game ID as the input parameter
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    Game getGame(String gameID) throws DataAccessException;
    /**
     * Returns all past and current games that are in the database.
     * @return  the array list of game objects currently in the database
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    ArrayList<Game> getAllGames() throws DataAccessException;
    /**
     * Claims the spot for a player into a new game.
     * The input user's username will be stored as either the white or black player's username.
     * @param u the user object to be added into the new game
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static boolean claimSpot(int gameID, String playerColor, String auth) throws DataAccessException {
        int userID = AuthDAO.getUserID(auth);
        String username = UserDAO.getUsername(userID);
        for (int i = 0; i < gameDB.size(); ++i) {
            if (gameDB.get(i).getGameID() == gameID) {
                if (Objects.equals(playerColor, "WHITE") && gameDB.get(i).getWhiteUsername() == null) {
                    gameDB.get(i).setWhiteUsername(username);
                    return true;
                }
                else if (Objects.equals(playerColor, "BLACK") && gameDB.get(i).getBlackUsername() == null) {
                    gameDB.get(i).setBlackUsername(username);
                    return true;
                }
                else if (Objects.equals(playerColor, "")) {
                    gameDB.get(i).addObserver(username);
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;
    }
    /**
     * Updates the game by replacing the game's ID with the new ID found in the game object.
     * @param g the game object with the new game ID
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    void updateGame(Game g) throws DataAccessException;
    /**
     * Deletes the game from the database.
     * @param g the game object to be deleted
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    void deleteGame(Game g) throws DataAccessException;
    /**
     * Clears all the information within the database by deleting all games.
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static void clear() {
        gameDB.clear();
    };

    static ArrayList<Game> listGames(String auth) throws DataAccessException {
        if (AuthDAO.isFound(auth)) {
            return gameDB;
        }
        return null;
    }
}
