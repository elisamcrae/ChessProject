package dataAccess;

import model.AuthToken;
import model.Game;
import model.User;

import java.util.ArrayList;

public class MemoryDataAccess implements DataAccess{
    @Override
    public void createUser(User u) throws DataAccessException {

    }

    @Override
    public void createGame(Game g) throws DataAccessException {

    }

    @Override
    public User getUser(AuthToken a) throws DataAccessException {
        return null;
    }

    @Override
    public Game getGame(String gameID) throws DataAccessException {
        return null;
    }

    @Override
    public ArrayList<Game> getAllGames() throws DataAccessException {
        return null;
    }

    @Override
    public void claimSpot(User u) throws DataAccessException {

    }

    @Override
    public void updateUser(User u) throws DataAccessException {

    }

    @Override
    public void updateGame(Game g) throws DataAccessException {

    }

    @Override
    public void updateAuthToken(AuthToken a) throws DataAccessException {

    }

    @Override
    public void deleteUser(User u) throws DataAccessException {

    }

    @Override
    public void deleteGame(Game g) throws DataAccessException {

    }

    @Override
    public void deleteAuthToken(AuthToken a) throws DataAccessException {

    }

    @Override
    public void clearAll() throws DataAccessException {
    }
}
