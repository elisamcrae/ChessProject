package dataAccess;

import model.Game;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores and retrieves game objects
 */
public interface GameDAO {
    /**
     * Database which stores all the games
     */
    ArrayList<Game> gameDB = GameDAOMemory.getGameDatabase();
    /**
     * Creates a game in the database given the game object.
     *
     * @param g the game object to be stored in the database
     * @param auth  the authentication string correlating to an authentication token object
     * @return true if the auth was found and the game was added, otherwise returns false
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    static boolean createGame(Game g, String auth) throws DataAccessException {
        if (AuthDAO.isFound(auth)) {
            gameDB.add(g);
            return true;
        }
        return false;
    }

    /**
     * Attempts to find the game within the database.
     *
     * @param gameID    the int game-specific ID to be located
     * @return  true if the game was found, otherwise returns false
     */
    static boolean isFound(int gameID) {
        for (Game game : gameDB) {
            if (game.getGameID() == gameID) {
                return true;
            }
        }
        return false;
    }

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
     * Clears all the information within the database by deleting all games.
     */
    static void clear() {
        gameDB.clear();
    };

    /**
     * Lists all games in database.
     *
     * @param auth  the string authentication to be verified before returning games
     * @return  game database in the form of an array
     * @throws DataAccessException  if database cannot be located
     */
    static ArrayList<Game> listGames(String auth) throws DataAccessException {
        if (AuthDAO.isFound(auth)) {
            return gameDB;
        }
        return null;
    }
}
