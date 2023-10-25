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
    ArrayList<Game> gameDB = GameDAOMemory.getGameDatabase();
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
        for (Game game : gameDB) {
            if (game.getGameID() == gameID) {
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
     * Claims the spot for a player into a new game.
     * The input user's username will be stored as either the white or black player's username.
     *
     * @param gameID    the int ID associated with the game to be watched
     * @param playerColor   the string with the color name the player will be. A null playerColor will be an observer
     * @param auth  the authentication token to be checked before adding a player to the game
     * @return  true if the player was added to the game and false otherwise
     * @throws DataAccessException  the data could not be accessed
     */
    static boolean claimSpot(int gameID, String playerColor, String auth) throws DataAccessException {
        int userID = AuthDAO.getUserID(auth);
        String username = UserDAO.getUsername(userID);
        if (userID == -10000 | username == null) {
            return false;
        }
        for (Game game : gameDB) {
            if (game.getGameID() == gameID) {
                if (Objects.equals(playerColor, "WHITE") && game.getWhiteUsername() == null) {
                    game.setWhiteUsername(username);
                    return true;
                } else if (Objects.equals(playerColor, "BLACK") && game.getBlackUsername() == null) {
                    game.setBlackUsername(username);
                    return true;
                } else if (Objects.equals(playerColor, "") | playerColor == null) {
                    game.addObserver(username);
                    return true;
                } else {
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
