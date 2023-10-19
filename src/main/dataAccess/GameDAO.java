package dataAccess;

import model.Game;
import model.User;

import java.util.ArrayList;

/**
 * Stores and retrieves game objects
 */
public interface GameDAO {
    /**
     * Creates a game in the database given the game object
     * @param g the game object to be stored in the database
     * @throws DataAccessException  exception thrown if the database cannot be accessed properly
     */
    void createGame(Game g) throws DataAccessException;
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
    void claimSpot(User u) throws DataAccessException;
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
    void clear() throws DataAccessException;
}
