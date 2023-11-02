package dataAccess;

import model.Game;

import java.util.ArrayList;

/**
 * Implementation of GameDAO when data is stored in memory
 */
public class GameDAOMemory implements GameDAO {
    /**
     * Database holding all games
     */
    private static ArrayList<Game> gameDatabase = new ArrayList<>();

    public static ArrayList<Game> getGameDatabase() {
        return gameDatabase;
    }

    public static void setGameDatabase(ArrayList<Game> gameDatabase) {
        GameDAOMemory.gameDatabase = gameDatabase;
    }
}
