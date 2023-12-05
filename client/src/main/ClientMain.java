import chess.*;
import model.Game;
import responses.*;
import java.util.*;
import static ui.EscapeSequences.*;

public class ClientMain {
    private Boolean loggedIn = false;
    private String loggedInAuth = "";
    private ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;
    private final ClientServerFacade server = new ClientServerFacade();
    private WSClient ws;

    public static void main(String[] args) throws Exception {
        var serverURL = "http://localhost:8080";
        if (args.length == 1) {
            serverURL = args[0];
        }
        new Repl(serverURL).run();
    }
    public String eval(String input) throws Exception {
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
        else if (Objects.equals(userInputs[0], "make_move")) {
            return move(userInputs[1], userInputs[2], userInputs[3], userInputs[4], userInputs[5]);
        }
        else if (Objects.equals(userInputs[0], "redraw_chess_board")) {
            return redraw(userInputs[1]);
        }
        else if (Objects.equals(userInputs[0], "highlight_moves")) {
            return highlight(userInputs[1], userInputs[2], userInputs[3]);
        }
        else if (Objects.equals(userInputs[0], "resign")) {
            return resign(userInputs[1]);
        }
        else if (Objects.equals(userInputs[0], "leave")) {
            return leave(userInputs[1]);
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
    public String join(String gameID, String playerColor) throws Exception {
        if (!loggedIn) {
            return "ERROR";
        }
        int gID = Integer.parseInt(gameID);
        ChessGame.TeamColor tc = ChessGame.TeamColor.WHITE;
        if (playerColor.equalsIgnoreCase("black")) {
            tc = ChessGame.TeamColor.BLACK;
        }
        this.color = tc;
        String toReturn = "";
        try {
            JoinGameResponse response = server.join(gID, playerColor, loggedInAuth);
            toReturn = """
                Commands:
                redraw_chess_board <GAMEID>
                leave
                make_move <ROW1> <COL1> <ROW2> <COL2> <GAMEID>
                resign
                highlight_moves <GAMEID> <ROW> <COL>
                """;
        } catch (Exception e) {
            gID = -1000;
        }
        ws = new WSClient();
        ws.join(loggedInAuth, gID, tc);
        return toReturn;
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
            if (games.isEmpty()) {
                s.append("No games created.");
            }
            return s.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String observe(String gameID) {
        int gID = Integer.parseInt(gameID);
        if (!loggedIn) {return "ERROR";}
        try {
            JoinGameResponse response = server.join(gID, loggedInAuth);
            //printBoard(response.getG());
        } catch (Exception e) {
            gID = -1000;
        }
        try {
            ws = new WSClient();
            ws.observe(loggedInAuth, gID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public static void printBoard(ChessGame myGame, ChessGame.TeamColor color) {
        ChessBoard board = myGame.getBoard();
        //CREATE DICT
        Dictionary boardDict = new Hashtable();
        boardDict.put(ChessPiece.PieceType.ROOK, " R ");
        boardDict.put(ChessPiece.PieceType.KNIGHT, " N ");
        boardDict.put(ChessPiece.PieceType.BISHOP, " B ");
        boardDict.put(ChessPiece.PieceType.QUEEN, " Q ");
        boardDict.put(ChessPiece.PieceType.KING, " K ");
        boardDict.put(ChessPiece.PieceType.PAWN, " P ");
        if (color == ChessGame.TeamColor.WHITE) {
            int counter = -1;
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.println("    h  g  f  e  d  c  b  a ");
            for (int i = -1; i < 7; ++i) {
                System.out.print(RESET_BG_COLOR);
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(SET_TEXT_BOLD);
                System.out.print(" " + String.valueOf(i + 1) + " ");
                for (int j = -1; j < 7; ++j) {
                    ++counter;
                    if (counter % 2 == 0) {
                        System.out.print("\u001b[31;100m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i + 1, j + 1));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;100m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        //PRINT COLOR B
                        System.out.print("\u001b[31;107m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i + 1, j + 1));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;107m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    }
                }
                System.out.print(RESET_BG_COLOR);
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(SET_TEXT_BOLD);
                System.out.println(" " + String.valueOf(i + 1) + " ");
                ++counter;
            }
            System.out.print(SET_TEXT_BOLD);
            System.out.println("    h  g  f  e  d  c  b  a ");
            System.out.print(RESET_BG_COLOR);
            System.out.print(RESET_TEXT_BOLD_FAINT);
        } else {
            int counter = -1;
//BOARD TWO
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.println("    a  b  c  d  e  f  g  h ");
            for (int i = 7; i > -1; --i) {
                System.out.print(RESET_BG_COLOR);
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(SET_TEXT_BOLD);
                System.out.print(" " + String.valueOf(i) + " ");
                for (int j = 7; j > -1; --j) {
                    ++counter;
                    if (counter % 2 == 0) {
                        System.out.print("\u001b[31;100m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i, j));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;100m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        //PRINT COLOR B
                        System.out.print("\u001b[31;107m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i, j));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;107m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
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
            System.out.print(RESET_BG_COLOR);
            System.out.print(RESET_TEXT_BOLD_FAINT);
        }
    }
    public String redraw(String gameID) throws Exception {
        int gID = Integer.parseInt(gameID);
        ListGamesResponse response = server.list(loggedInAuth);
        ArrayList<Game> games = response.getGames();
        Game myGame = null;
        for (Game game : games) {
            if (game.getGameID() == gID) {
                myGame = game;
                break;
            }
        }
        assert myGame != null;
        printBoard(myGame.getGame(), color);
        return "";
    }
    public String highlight(String gameID, String row, String col) throws Exception {
        ChessPosition pos = new ChessPositionE(Integer.parseInt(row)-1, Integer.parseInt(col)-1);
        int gID = Integer.parseInt(gameID);
        ListGamesResponse response = server.list(loggedInAuth);
        ArrayList<Game> games = response.getGames();
        Game myGame = null;
        for (Game game : games) {
            if (game.getGameID() == gID) {
                myGame = game;
                break;
            }
        }
        assert myGame != null;
        ChessGame toDel = myGame.getGame();
        Collection<ChessMove> validMoves = myGame.getGame().validMoves(pos);
        highlightMoves(myGame.getGame(), validMoves);
        return "";
    }
    public void highlightMoves(ChessGame g, Collection<ChessMove> validMoves) {
        ChessBoard board = g.getBoard();
        ArrayList<ChessPosition> validEnds = new ArrayList<>();
        for (ChessMove move : validMoves) {
            validEnds.add(move.getEndPosition());
        }
        //CREATE DICT
        Dictionary boardDict = new Hashtable();
        boardDict.put(ChessPiece.PieceType.ROOK, " R ");
        boardDict.put(ChessPiece.PieceType.KNIGHT, " N ");
        boardDict.put(ChessPiece.PieceType.BISHOP, " B ");
        boardDict.put(ChessPiece.PieceType.QUEEN, " Q ");
        boardDict.put(ChessPiece.PieceType.KING, " K ");
        boardDict.put(ChessPiece.PieceType.PAWN, " P ");
        if (color == ChessGame.TeamColor.WHITE) {
            int counter = -1;
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.println("    h  g  f  e  d  c  b  a ");
            for (int i = -1; i < 7; ++i) {
                System.out.print(RESET_BG_COLOR);
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(SET_TEXT_BOLD);
                System.out.print(" " + String.valueOf(i + 1) + " ");
                for (int j = -1; j < 7; ++j) {
                    ++counter;
                    if (validEnds.contains(new ChessPositionE(i+1,j+1))) {
                        System.out.print("\u001b[31;105m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i+1, j+1));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;105m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    }
                    else if (counter % 2 == 0) {
                        System.out.print("\u001b[31;100m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i + 1, j + 1));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;100m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        //PRINT COLOR B
                        System.out.print("\u001b[31;107m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i + 1, j + 1));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;107m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    }
                }
                System.out.print(RESET_BG_COLOR);
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(SET_TEXT_BOLD);
                System.out.println(" " + String.valueOf(i + 1) + " ");
                ++counter;
            }
            System.out.print(SET_TEXT_BOLD);
            System.out.println("    h  g  f  e  d  c  b  a ");
            System.out.print(RESET_BG_COLOR);
            System.out.print(RESET_TEXT_BOLD_FAINT);
        } else {
            int counter = -1;
//BOARD TWO
            System.out.print(RESET_BG_COLOR);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_WHITE);
            System.out.println("    a  b  c  d  e  f  g  h ");
            for (int i = 7; i > -1; --i) {
                System.out.print(RESET_BG_COLOR);
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(SET_TEXT_BOLD);
                System.out.print(" " + String.valueOf(i) + " ");
                for (int j = 7; j > -1; --j) {
                    ++counter;
                    if (validEnds.contains(new ChessPositionE(i,j))) {
                        System.out.print("\u001b[31;102m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i, j));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;102m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    }
                    else if (counter % 2 == 0) {
                        System.out.print("\u001b[31;100m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i, j));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;100m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
                            System.out.print("   ");
                        }
                    } else {
                        //PRINT COLOR B
                        System.out.print("\u001b[31;107m");
                        ChessPiece c = board.getPiece(new ChessPositionE(i, j));
                        if (c != null) {
                            if (c.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                System.out.print(boardDict.get(c.getPieceType()));
                            } else {
                                System.out.print("\u001b[34;107m");
                                System.out.print(boardDict.get(c.getPieceType()));
                            }
                        } else {
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
            System.out.print(RESET_BG_COLOR);
            System.out.print(RESET_TEXT_BOLD_FAINT);
        }
    }
    public String resign(String gameID) {
        int gID = Integer.parseInt(gameID);
        try {
            ws = new WSClient();
            ws.resign(loggedInAuth, gID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public String leave(String gameID) {
        int gID = Integer.parseInt(gameID);
        try {
            ws = new WSClient();
            ws.leave(loggedInAuth, gID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
    public String move(String one, String two, String oneb, String twob, String gameID) throws Exception {
        ChessPosition pos1 = new ChessPositionE(Integer.parseInt(one), Integer.parseInt(two));
        ChessPosition pos2 = new ChessPositionE(Integer.parseInt(oneb), Integer.parseInt(twob));
        ChessMove move = new ChessMoveE(pos1, pos2);
        int gID = Integer.parseInt(gameID);

        try {
            ws = new WSClient();
            ws.move(loggedInAuth, gID, move);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            return "";
        }
    }
}
