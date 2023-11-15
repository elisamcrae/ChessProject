import chess.ChessGame;
import model.Game;
import org.junit.jupiter.api.*;
import responses.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ClientServerFacadeTest {
    private String authToken = "";
    private final ClientServerFacade server = new ClientServerFacade();
    private int gameID;
    @Test
    @Order(1)
    void registerPositive() {
        //Register like normal
        ArrayList<String> params = new ArrayList<>();
        params.add("username");
        params.add("password");
        params.add("email");
        try {
            RegisterResponse r = server.register(params);
            authToken = r.getAuth();
            Assertions.assertEquals("Success!", r.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void registerNegative() {
        //Register same user twice
        ArrayList<String> params = new ArrayList<>();
        params.add("username");
        params.add("password");
        params.add("email");
        try {
            Assertions.assertThrows(IOException.class, ()->{server.register(params);});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    void loginPositive() {
        ArrayList<String> p = new ArrayList<>();
        p.add("username");
        p.add("password");
        try {
            LoginResponse r = server.login(p);
            authToken = r.getAuthToken();
            Assertions.assertEquals("Success!", r.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    void loginNegative() {
        //USERNAME NOT IN DATABASE
        ArrayList<String> p = new ArrayList<>();
        p.add("newUsername");
        p.add("password");
        try {
            Assertions.assertThrows(IOException.class, ()->{server.login(p);});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    void logoutPositive() {
        loginPositive();
        //NORMAL LOGOUT
        try {
            Assertions.assertDoesNotThrow(()->{server.logout(authToken);});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(6)
    void logoutNegative() {
        //BAD AUTH TOKEN
        try {
            Assertions.assertThrows(IOException.class, ()->{server.logout("badAuthToken");});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(7)
    void createPositive() {
        loginPositive();
        try {
            CreateGameResponse r = server.create("myFirstGame!", authToken);
            gameID = r.getGameID();
            Assertions.assertEquals("Success!", r.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(8)
    void createNegative() {
        //Bad auth token
        try {
            Assertions.assertThrows(IOException.class, ()->{server.create("mySecondGame!", "badAuthToken");});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(9)
    void joinPositive() {
        loginPositive();
        createPositive();
        try {
            JoinGameResponse response = server.join(gameID, "WHITE", authToken);
            Assertions.assertEquals("Success!", response.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(10)
    void joinNegative() {
        //TRY TO JOIN IN FILLED POSITION
        loginPositive();
        createPositive();
        try {
            server.join(gameID, "WHITE", authToken);
            Assertions.assertThrows(IOException.class, ()->{server.join(gameID, "WHITE", authToken);});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(11)
    void observePositive() {
        loginPositive();
        createPositive();
        try {
            JoinGameResponse response = server.join(gameID, authToken);
            Assertions.assertEquals("Success!", response.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(12)
    void observeNegative() {
        //TRY TO OBSERVE WITH BAD GAME ID
        loginPositive();
        try {
            Assertions.assertThrows(IOException.class, ()->{server.join(109, authToken);});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(13)
    void listPositive() {
        loginPositive();
        ListGamesResponse response = null;
        try {
            response = server.list(authToken);
            ArrayList<Game> games = response.getGames();
            int myLength = games.size();
            Assertions.assertEquals(4, myLength);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(14)
    void listNegative() {
        //GIVE BAD AUTH TOKEN
        Assertions.assertThrows(IOException.class, ()->{server.list("badAuthToken");});
    }
}