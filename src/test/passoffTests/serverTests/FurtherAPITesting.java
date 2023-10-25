package passoffTests.serverTests;

import chess.ChessGame;
import model.AuthToken;
import org.junit.jupiter.api.*;
import passoffTests.TestFactory;
import passoffTests.obfuscatedTestClasses.TestServerFacade;
import passoffTests.testClasses.TestModels;

import java.util.*;

@SuppressWarnings("unused")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FurtherAPITesting {
    private static final int HTTP_OK = 200;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_UNAUTHORIZED = 401;
    private static final int HTTP_FORBIDDEN = 403;

    private static TestModels.TestUser existingUser;
    private static TestModels.TestUser newUser;
    private static TestModels.TestCreateRequest createRequest;
    private static TestServerFacade serverFacade;
    private String existingAuth;


    @BeforeAll
    public static void init() {
        existingUser = new TestModels.TestUser();
        existingUser.username = "Joseph";
        existingUser.password = "Smith";
        existingUser.email = "urim@thummim.net";

        newUser = new TestModels.TestUser();
        newUser.username = "testUsername";
        newUser.password = "testPassword";
        newUser.email = "testEmail";

        createRequest = new TestModels.TestCreateRequest();
        createRequest.gameName = "testGame";

        serverFacade = new TestServerFacade("localhost", TestFactory.getServerPort());
    }


    @BeforeEach
    public void setup() {
        serverFacade.clear();

        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = existingUser.password;
        registerRequest.email = existingUser.email;

        //one user already logged in
        TestModels.TestLoginRegisterResult regResult = serverFacade.register(registerRequest);
        existingAuth = regResult.authToken;
    }


    @Test
    @Order(1)
    @DisplayName("Normal User Login - Positive")
    public void successLoginTwice() {
        //Log in two times in succession without logging out.
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;

        TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);
        TestModels.TestLoginRegisterResult loginResult1 = serverFacade.login(loginRequest);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(loginResult1.success, "Response returned not successful");
        Assertions.assertFalse(
                loginResult1.message != null && loginResult1.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response returned error message");
        Assertions.assertEquals(existingUser.username, loginResult1.username,
                "Response did not give the same username as user");
        Assertions.assertNotNull(loginResult1.authToken, "Response did not return authentication String");
    }


    @Test
    @Order(2)
    @DisplayName("User Login - Negative")
    public void loginInvalidUsername() {
        //Attempting to log in with a null username
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = null;
        loginRequest.password = newUser.password;

        TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);

        Assertions.assertEquals(HTTP_UNAUTHORIZED, serverFacade.getStatusCode(),
                "Server response code was not 401 Unauthorized");
        Assertions.assertFalse(loginResult.success, "Response didn't return not successful");
        Assertions.assertTrue(loginResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response missing error message");
        Assertions.assertNull(loginResult.username, "Response incorrectly returned username");
        Assertions.assertNull(loginResult.authToken, "Response incorrectly return authentication String");
    }

    @Test
    @Order(3)
    @DisplayName("Normal User Registration - Positive")
    public void successRegisterWithRepeatUsername() {
        //reusing a username
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = existingUser.username;
        registerRequest.password = newUser.password;
        registerRequest.email = newUser.email;

        //submit register request
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(registerResult.success,
                "server.Server did not say registration was successful for new user.");
        Assertions.assertFalse(
                registerResult.message != null && registerResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response gave an error message");
        Assertions.assertEquals(existingUser.username, registerResult.username,
                "Response did not have the same username as was registered");
        Assertions.assertNotNull(registerResult.authToken, "Response did not contain an authentication string");
    }


    @Test
    @Order(4)
    @DisplayName("User Registration - Negative")
    public void registerTwice() {
        //create request trying to register existing user
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = newUser.username;
        registerRequest.password = newUser.password;
        registerRequest.email = null;

        //submit register request
        TestModels.TestLoginRegisterResult registerResult = serverFacade.register(registerRequest);

        Assertions.assertEquals(HTTP_BAD_REQUEST, serverFacade.getStatusCode(),
                "Server response code was not 400 Bad Request");
        Assertions.assertFalse(registerResult.success, "Response didn't return not successful");
        Assertions.assertTrue(registerResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response missing error message");
        Assertions.assertNull(registerResult.username, "Response incorrectly contained username");
        Assertions.assertNull(registerResult.authToken, "Response incorrectly contained authentication string");
    }

    @Test
    @Order(5)
    @DisplayName("Normal Logout - Positive")
    public void successLogoutNotMostRecent() {
        //log out user that was not the most recently logged in
        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;
        TestModels.TestLoginRegisterResult loginResult = serverFacade.login(loginRequest);
        String myAuthToken = loginResult.authToken;

        TestModels.TestLoginRequest loginRequest1 = new TestModels.TestLoginRequest();
        loginRequest.username = existingUser.username;
        loginRequest.password = existingUser.password;
        TestModels.TestLoginRegisterResult loginResult1 = serverFacade.login(loginRequest1);

        TestModels.TestResult result = serverFacade.logout(myAuthToken);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(result.success, "Response didn't return successful");
        Assertions.assertFalse(result.message != null &&
                        result.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response gave an error message");
    }


    @Test
    @Order(6)
    @DisplayName("Logout - Negative")
    public void failLogoutBadAuth() {
        //authToken is not found
        String newAuth = UUID.randomUUID().toString();
        TestModels.TestResult result = serverFacade.logout(newAuth);

        Assertions.assertEquals(HTTP_UNAUTHORIZED, serverFacade.getStatusCode(),
                "Server response code was not 401 Unauthorized");
        Assertions.assertFalse(result.success, "Response didn't return not successful");
        Assertions.assertTrue(result.message.toLowerCase(Locale.ROOT).contains("error"),
                "Response did not return error message");
    }

    @Test
    @Order(7)
    @DisplayName("Game Creation - Positive")
    public void goodCreate() {
        //create two games with the same game name
        serverFacade.createGame(createRequest, existingAuth);
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(createResult.success, "Result did not return successful");
        Assertions.assertFalse(
                createResult.message != null && createResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
        Assertions.assertNotNull(createResult.gameID, "Result did not return a game ID");
        Assertions.assertTrue(createResult.gameID > 0, "Result returned invalid game ID");
    }


    @Test
    @Order(8)
    @DisplayName("Game Create - Negative")
    public void badCreateNoGameName() {
        createRequest.gameName = null;
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        Assertions.assertEquals(HTTP_BAD_REQUEST, serverFacade.getStatusCode(),
                "Server response code was not 400 Bad Request");
        Assertions.assertFalse(createResult.success, "Bad result didn't return not successful");
        Assertions.assertTrue(createResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Bad result did not return an error message");
        Assertions.assertNull(createResult.gameID, "Bad result returned a game ID");
    }

    @Test
    @Order(9)
    @DisplayName("Join Game - Positive")
    public void goodJoin() {
        //have both players join
        //create game
        createRequest.gameName = "testGame";
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        //join as white
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        serverFacade.verifyJoinPlayer(joinRequest, existingAuth);

        //log out of white
        TestModels.TestResult result = serverFacade.logout(existingAuth);

        //create new player to be black player
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "newUsername";
        registerRequest.password = "newPassword";
        registerRequest.email = "newEmail";
        TestModels.TestLoginRegisterResult regResult1 = serverFacade.register(registerRequest);
        String newAuth = regResult1.authToken;

        TestModels.TestLoginRequest loginRequest = new TestModels.TestLoginRequest();
        loginRequest.username = "newUsername";
        loginRequest.password = "newPassword";
        serverFacade.login(loginRequest);

        //join as black
        TestModels.TestJoinRequest joinRequest1 = new TestModels.TestJoinRequest();
        joinRequest1.gameID = createResult.gameID;
        joinRequest1.playerColor = ChessGame.TeamColor.BLACK;
        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest1, newAuth);

        //check
        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(joinResult.success, "Request returned not successful");
        Assertions.assertFalse(
                joinResult.message != null && joinResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");

        TestModels.TestListResult listResult = serverFacade.listGames(newAuth);

        Assertions.assertEquals(1, listResult.games.length);
        Assertions.assertEquals(existingUser.username, listResult.games[0].whiteUsername);
        Assertions.assertEquals("newUsername", listResult.games[0].blackUsername);
    }


    @Test
    @Order(10)
    @DisplayName("Join Game - Negative")
    public void badJoin() {
        //null auth token
        //create game
        TestModels.TestCreateResult createResult = serverFacade.createGame(createRequest, existingAuth);

        //join as white
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.gameID = createResult.gameID;
        joinRequest.playerColor = ChessGame.TeamColor.valueOf("WHITE");
        TestModels.TestResult joinResult = serverFacade.verifyJoinPlayer(joinRequest, null);

        //check
        Assertions.assertEquals(HTTP_UNAUTHORIZED, serverFacade.getStatusCode(), "Server response code was not 400 Unauthorized");
        Assertions.assertFalse(joinResult.success, "Request returned successful");
        Assertions.assertTrue(
                joinResult.message != null && joinResult.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }

    @Test
    @Order(11)
    @DisplayName("List Games - Positive")
    public void gamesList() {
        //List games that are duplicates in everything except game ID
        //register a few users to create games
        TestModels.TestRegisterRequest registerRequest = new TestModels.TestRegisterRequest();
        registerRequest.username = "a";
        registerRequest.password = "A";
        registerRequest.email = "a.A";
        TestModels.TestLoginRegisterResult userA = serverFacade.register(registerRequest);

        registerRequest.username = "b";
        registerRequest.password = "B";
        registerRequest.email = "b.B";
        TestModels.TestLoginRegisterResult userB = serverFacade.register(registerRequest);

        //create games

        createRequest.gameName = "Here";
        TestModels.TestCreateResult game1 = serverFacade.createGame(createRequest, userA.authToken);

        createRequest.gameName = "Here";
        TestModels.TestCreateResult game2 = serverFacade.createGame(createRequest, userA.authToken);

        createRequest.gameName = "Here";
        TestModels.TestCreateResult game3 = serverFacade.createGame(createRequest, userA.authToken);

        //B join game 1 as black
        TestModels.TestJoinRequest joinRequest = new TestModels.TestJoinRequest();
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game1.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userB.authToken);

        //A join game 1 as white
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game1.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userA.authToken);

        //B join game 2 as black
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game2.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userB.authToken);

        //A join game 2 as white
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game2.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userA.authToken);

        //B join game3 as black
        joinRequest.playerColor = ChessGame.TeamColor.BLACK;
        joinRequest.gameID = game3.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userB.authToken);

        //A join game3 as white
        joinRequest.playerColor = ChessGame.TeamColor.WHITE;
        joinRequest.gameID = game3.gameID;
        serverFacade.verifyJoinPlayer(joinRequest, userA.authToken);

        //create expected entry items
        Collection<TestModels.TestListResult.TestListEntry> expectedList = new HashSet<>();

        //game 1
        TestModels.TestListResult.TestListEntry entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game1.gameID;
        entry.gameName = "Here";
        entry.whiteUsername = userA.username;
        entry.blackUsername = userB.username;
        expectedList.add(entry);

        //game 2
        entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game2.gameID;
        entry.gameName = "Here";
        entry.whiteUsername = userA.username;
        entry.blackUsername = userB.username;
        expectedList.add(entry);

        //game 3
        entry = new TestModels.TestListResult.TestListEntry();
        entry.gameID = game3.gameID;
        entry.gameName = "Here";
        entry.whiteUsername = userA.username;
        entry.blackUsername = userB.username;
        expectedList.add(entry);

        //list games
        TestModels.TestListResult listResult = serverFacade.listGames(existingAuth);
        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Collection<TestModels.TestListResult.TestListEntry> returnedList =
                new HashSet<>(Arrays.asList(listResult.games));

        //check
        Assertions.assertEquals(expectedList, returnedList, "Returned Games list was incorrect");
    }

    @Test
    @Order(12)
    @DisplayName("List Games - Negative")
    public void badGamesList() {
        //invalid auth token
        String newAuth = UUID.randomUUID().toString();
        TestModels.TestListResult result = serverFacade.listGames(newAuth);

        Assertions.assertEquals(HTTP_UNAUTHORIZED, serverFacade.getStatusCode(), "Server response code was not 400 Unauthorized");
        Assertions.assertFalse(result.success, "Result returned successful.");
        Assertions.assertTrue(result.games == null || result.games.length == 0,
                "Found games when none should be there");
        Assertions.assertTrue(result.message != null && result.message.toLowerCase(Locale.ROOT).contains("error"),
                "Result returned an error message");
    }

    @Test
    @Order(13)
    @DisplayName("Clear Test - Positive")
    public void EmptyClear() {

        //clear when everything is already empty
        serverFacade.clear();
        TestModels.TestResult result = serverFacade.clear();

        Assertions.assertEquals(HTTP_OK, serverFacade.getStatusCode(), "Server response code was not 200 OK");
        Assertions.assertTrue(result.success, "Clear Response came back not successful");
        Assertions.assertFalse(result.message != null && result.message.toLowerCase(Locale.ROOT).contains("error"),
                "Clear Result returned an error message");
    }

}


