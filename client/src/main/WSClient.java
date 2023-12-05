import Communication.*;
import chess.*;
import com.google.gson.*;
import webSocketMessages.serverMessages.ServerMessage;
import javax.websocket.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Dictionary;
import java.util.Hashtable;
import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_BOLD;

public class WSClient extends Endpoint {
    public Session session;
    private ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;
    public WSClient() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ServerMessage sm = new Gson().fromJson(message, ServerMessage.class);
                switch (sm.getServerMessageType()) {
                    case LOAD_GAME:
                        var builder = new GsonBuilder();
                        builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
                        builder.registerTypeAdapter(ChessBoardE.class, new ChessBoardAdapter());
                        builder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
                        LoadGameSMessage g = builder.create().fromJson(message, LoadGameSMessage.class);
                        printBoard(g.game, color);
                        break;
                    case NOTIFICATION, ERROR:
                        System.out.println(sm.getMessage());
                        break;
                }
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void join(String auth, int gameID, ChessGame.TeamColor color) throws Exception {
        JoinPlayerUCommand j = new JoinPlayerUCommand(auth, gameID, color);
        this.color = color;
        send(new Gson().toJson(j));
    }

    public void observe(String auth, int gameID) throws Exception {
        JoinObserverUCommand j = new JoinObserverUCommand(auth, gameID);
        send(new Gson().toJson(j));
    }

    public void move(String auth, int gameID, ChessMove move) throws Exception {
        MakeMoveUCommand u = new MakeMoveUCommand(auth, gameID, move);
        send(new Gson().toJson(u));
    }
    public void resign(String auth, int gameID) throws Exception {
        ResignUCommand r = new ResignUCommand(auth, gameID);
        send(new Gson().toJson(r));
    }

    public void leave(String auth, int gameID) throws Exception {
        LeaveUCommand l = new LeaveUCommand(auth, gameID);
        send(new Gson().toJson(l));
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
}

class ChessGameAdapter implements JsonSerializer<ChessGame>, JsonDeserializer<ChessGame> {
    @Override
    public JsonElement serialize(ChessGame src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("teamTurn", src.getTeamTurn().toString());
        jsonObject.add("board", context.serialize(src.getBoard()));
        return jsonObject;
    }

    @Override
    public ChessGame deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        ChessGame chessGame = new ChessGameE();

        if (jsonObject.has("teamTurn")) {
            chessGame.setTeamTurn(ChessGame.TeamColor.valueOf(jsonObject.get("teamTurn").getAsString()));
        }

        if (jsonObject.has("board")) {
            chessGame.setBoard(context.deserialize(jsonObject.get("board"), ChessBoardE.class));
        }

        return chessGame;
    }
}
