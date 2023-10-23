package dataAccess;

import chess.ChessGameE;
import model.Game;
import model.User;

import java.util.ArrayList;

/**
 * Implementation of GameDAO when data is stored in memory
 */
public class GameDAOMemory implements GameDAO{
    private static ArrayList<Game> gameDatabase = new ArrayList<>();

    @Override
    public void createGame(Game g) throws DataAccessException {
        gameDatabase.add(g);
    }

    @Override
    public Game getGame(String gameID) throws DataAccessException {
        return null;
    }

    @Override
    public ArrayList<Game> getAllGames() throws DataAccessException {
        return gameDatabase;
    }

    @Override
    public void claimSpot(User u) throws DataAccessException {
        //add user to the last game in gameDatabase?
        //if white user is null, add username to white username space?
    }

    @Override
    public void updateGame(Game g) throws DataAccessException {
        for (int i = 0; i < gameDatabase.size(); ++i) {
            if (gameDatabase.get(i).getGameID() == g.getGameID()) {
                gameDatabase.set(i, g);
            }
        }
    }

    @Override
    public void deleteGame(Game g) throws DataAccessException {
        gameDatabase.remove(g);
    }
}
