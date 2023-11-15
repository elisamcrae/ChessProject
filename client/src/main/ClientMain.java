import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPositionE;
import model.Game;
import responses.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

import static ui.EscapeSequences.*;

public class ClientMain {
    private Boolean loggedIn = false;
    private String loggedInAuth = "";
    private final ClientServerFacade server = new ClientServerFacade();

    public static void main(String[] args) {
        var serverURL = "http://localhost:8080";
        if (args.length == 1) {
            serverURL = args[0];
        }
        new Repl(serverURL).run();
    }

    public String eval(String input) {
        String[] userInputs = input.split(" ");
        if (Objects.equals(userInputs[0], "help")) {
            return help();
        }
        else if (Objects.equals(userInputs[0], "quit")) {
            return quit();
        }
        else if (Objects.equals(userInputs[0], "login")) {
            return login(userInputs[1], userInputs[2]);
        }
        else if (Objects.equals(userInputs[0], "register")) {
            return register(userInputs[1], userInputs[2], userInputs[3]);
        }
        else if (Objects.equals(userInputs[0], "logout")) {
            return logout();
        }
        else if (Objects.equals(userInputs[0], "create")) {
            return create(userInputs[1]);
        }
        else if (Objects.equals(userInputs[0], "join")) {
            return join(userInputs[1], userInputs[2]);
        }
        else if (Objects.equals(userInputs[0], "list")) {
            return list();
        }
        else if (Objects.equals(userInputs[0], "observe")) {
            return observe(userInputs[1]);
        }
        return null;
    }

    public String help() {
        if (!loggedIn) {
            return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to play chess
                quit - stop the chess program
                help - with possible commands""";
        }
        else {
            //IF LOGGED IN:
            return """
                create <NAME> - a game
                list - games
                join <ID> [WHITE|BLACK|<empty>] - a game
                observe <ID> - a game
                logout - when you are done
                quit - stop the chess program
                help - with possible commands""";
        }
    }

    public String quit() {
        return null;
    }

    public String login(String username, String password) {
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);
        try {
            LoginResponse response = server.login(params);
            loggedIn = true;
            loggedInAuth = response.getAuthToken();
            return help();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String register(String username, String password, String email) {
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);
        params.add(email);
        try {
            RegisterResponse response = server.register(params);
            loggedIn = true;
            loggedInAuth = response.getAuth();
            return(help());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String logout() {
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            server.logout(loggedInAuth);
            loggedIn = false;
            return help();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String create(String name) {
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            CreateGameResponse response = server.create(name, loggedInAuth);
            return "Game created!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String join(String gameID, String playerColor) {
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            int gID = Integer.parseInt(gameID);
            JoinGameResponse response = server.join(gID, playerColor, loggedInAuth);
            printBoard(response.getG());
            return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String list() {
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            ListGamesResponse response = server.list(loggedInAuth);
            ArrayList<Game> games = response.getGames();
            StringBuilder s = new StringBuilder();
            for (Game game : games) {
                s.append("GameID: ").append(game.getGameID()).append(", Game Name: ").append(game.getGameName()).append(", White Player: ").append(game.getWhiteUsername()).append(", Black Player: ").append(game.getBlackUsername());
                s.append("\n");
            }
            return s.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String observe(String gameID) {
        int gID = Integer.parseInt(gameID);
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            JoinGameResponse response = server.join(gID, loggedInAuth);
            //printBoard(gameID);
            return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void printBoard(Game g) {
        assert g != null;
        ChessGame myGame = g.getGame();
        ChessBoard board = myGame.getBoard();
        //CREATE DICT
        Dictionary boardDict = new Hashtable();
        boardDict.put(ChessPiece.PieceType.ROOK, " R ");
        boardDict.put(ChessPiece.PieceType.KNIGHT, " N ");
        boardDict.put(ChessPiece.PieceType.BISHOP, " B ");
        boardDict.put(ChessPiece.PieceType.QUEEN, " Q ");
        boardDict.put(ChessPiece.PieceType.KING, " K ");
        boardDict.put(ChessPiece.PieceType.PAWN, " P ");

        int counter = -1;
        System.out.print(RESET_BG_COLOR);
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("    h  g  f  e  d  c  b  a ");
        for (int i = 0; i < 8; ++i) {
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(" " + String.valueOf(i+1) + " ");
            for (int j = 0; j < 8; ++j) {
                ++counter;
                if (counter % 2 == 0) {
                    System.out.print("\u001b[31;100m");
                    ChessPiece c = board.getPiece(new ChessPositionE(i + 1, j + 1));
                    if (c != null) {
                        if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(boardDict.get(c.getPieceType()));
                        }
                        else {
                            System.out.print("\u001b[34;100m");
                            System.out.print(boardDict.get(c.getPieceType()));
                        }
                    }
                    else {
                        System.out.print("   ");
                    }
                }
                else {
                    //PRINT COLOR B
                    System.out.print("\u001b[31;107m");
                    ChessPiece c = board.getPiece(new ChessPositionE(i + 1, j + 1));
                    if (c != null) {
                        if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(boardDict.get(c.getPieceType()));
                        }
                        else {
                            System.out.print("\u001b[34;107m");
                            System.out.print(boardDict.get(c.getPieceType()));
                        }                    }
                    else {
                        System.out.print("   ");
                    }
                }
            }
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.print(SET_TEXT_BOLD);
            System.out.println(" " + String.valueOf(i+1) + " ");
            ++counter;
        }
        System.out.print(SET_TEXT_BOLD);
        System.out.println("    h  g  f  e  d  c  b  a ");

        //BOARD TWO
        counter = -1;
        System.out.print(RESET_BG_COLOR);
        System.out.print(SET_TEXT_BOLD);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("    a  b  c  d  e  f  g  h ");
        for (int i = 8; i > 0; --i) {
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(" " + String.valueOf(i) + " ");
            for (int j = 8; j > 0; --j) {
                ++counter;
                if (counter % 2 == 0) {
                    System.out.print("\u001b[31;100m");
                    ChessPiece c = board.getPiece(new ChessPositionE(i , j ));
                    if (c != null) {
                        if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(boardDict.get(c.getPieceType()));
                        }
                        else {
                            System.out.print("\u001b[34;100m");
                            System.out.print(boardDict.get(c.getPieceType()));
                        }
                    }
                    else {
                        System.out.print("   ");
                    }
                }
                else {
                    //PRINT COLOR B
                    System.out.print("\u001b[31;107m");
                    ChessPiece c = board.getPiece(new ChessPositionE(i , j ));
                    if (c != null) {
                        if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                            System.out.print(boardDict.get(c.getPieceType()));
                        }
                        else {
                            System.out.print("\u001b[34;107m");
                            System.out.print(boardDict.get(c.getPieceType()));
                        }                    }
                    else {
                        System.out.print("   ");
                    }
                }
            }
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.print(SET_TEXT_BOLD);
            System.out.println(" " + String.valueOf(i) + " ");
            ++counter;
        }
        System.out.print(SET_TEXT_BOLD);
        System.out.println("    a  b  c  d  e  f  g  h ");
    }
}
