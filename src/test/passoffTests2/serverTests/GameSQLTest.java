package passoffTests2.serverTests;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import dataAccess.GameSQL;
import dataAccess.UserSQL;
import model.AuthToken;
import model.Game;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameSQLTest {

    @Test
    void createGamePositive() {
        try {
            GameSQL.clear();

            //Put auth into auth database
            AuthSQL.clear();
            AuthToken a = new AuthToken(1);
            AuthSQL.createAuth(a);

            Game g = new Game(null, null, "FirstGame!");
            Assertions.assertTrue(GameSQL.createGame(g, a.getAuthToken()));
            Assertions.assertTrue(GameSQL.isFound(g.getGameID()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createGameNegative() {
        try {
            GameSQL.clear();

            //Auth not in database
            AuthSQL.clear();
            AuthToken a = new AuthToken(1);

            Game g = new Game(null, null, "FirstGame!");
            Assertions.assertFalse(GameSQL.createGame(g, a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isFoundPositive() {
        try {
            GameSQL.clear();

            AuthSQL.clear();
            AuthToken a = new AuthToken(1);
            AuthSQL.createAuth(a);

            Game g = new Game(null, null, "FirstGame!");
            GameSQL.createGame(g, a.getAuthToken());
            Assertions.assertTrue(GameSQL.isFound(g.getGameID()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isFoundNegative() {
        try {
            GameSQL.clear();

            //Game not in database
            AuthSQL.clear();
            AuthToken a = new AuthToken(1);
            AuthSQL.createAuth(a);

            Game g = new Game(null, null, "FirstGame!");
            Game g2 = new Game("me", null, "SECOND GAME");
            GameSQL.createGame(g, a.getAuthToken());
            Assertions.assertFalse(GameSQL.isFound(g2.getGameID()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void claimSpotPositive() {
        try {
            GameSQL.clear();
            AuthSQL.clear();
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            AuthToken a = new AuthToken(u.getUserID());
            AuthSQL.createAuth(a);

            Game g = new Game(null, null, "FirstGame!");
            GameSQL.createGame(g, a.getAuthToken());
            Assertions.assertTrue(GameSQL.claimSpot(g.getGameID(),"WHITE", a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void claimSpotNegative() {
        //User doesn't have a username to put as observer
        try {
            GameSQL.clear();
            AuthSQL.clear();
            UserSQL.clear();
            User u = new User("username", "password", "email");
            AuthToken a = new AuthToken(u.getUserID());
            AuthSQL.createAuth(a);

            Game g = new Game(null, null, "FirstGame!");
            GameSQL.createGame(g, a.getAuthToken());
            Assertions.assertFalse(GameSQL.claimSpot(g.getGameID(),"WHITE", a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void clearPositive() {
        try {
            AuthSQL.clear();
            AuthToken a = new AuthToken(1);
            AuthSQL.createAuth(a);
            GameSQL.clear();

            Assertions.assertTrue(GameSQL.listGames(a.getAuthToken()).isEmpty());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void listGamesPositive() {
    }

    @Test
    void listGamesNegative() {
    }
}