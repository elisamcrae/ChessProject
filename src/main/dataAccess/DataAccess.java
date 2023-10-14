package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.util.ArrayList;

public interface DataAccess {
    void createUser(User u) throws DataAccessException;
    void createGame(Game g) throws DataAccessException;
    User getUser(AuthToken a) throws DataAccessException;
    Game getGame(String gameID) throws DataAccessException;
    ArrayList<Game> getAllGames() throws DataAccessException;
    void claimSpot(User u) throws DataAccessException;
    void updateUser(User u) throws DataAccessException;
    void updateGame(Game g) throws DataAccessException;
    void updateAuthToken(AuthToken a) throws DataAccessException;
    void deleteUser(User u) throws DataAccessException;
    void deleteGame(Game g) throws DataAccessException;
    void deleteAuthToken(AuthToken a) throws DataAccessException;
    void clearAll() throws DataAccessException;

}
