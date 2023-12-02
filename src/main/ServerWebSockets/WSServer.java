package ServerWebSockets;

import Communication.*;
import chess.*;
import com.google.gson.*;
import dataAccess.*;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import static java.sql.DriverManager.getConnection;

@WebSocket
public class WSServer {
    private ConcurrentHashMap<Integer, ArrayList<Session>> games = new ConcurrentHashMap<>();
    private ChessGame.TeamColor teamTurn = ChessGame.TeamColor.WHITE;
    private Boolean alreadyPrompted = false;
    @OnWebSocketMessage
    public void onMessage(org.eclipse.jetty.websocket.api.Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayerUCommand.class));
                case JOIN_OBSERVER -> observe(session, new Gson().fromJson(message, JoinObserverUCommand.class));
                case MAKE_MOVE -> move(session, message);
                case LEAVE -> leave(session, command);
                case RESIGN -> resign(session, new Gson().fromJson(message, ResignUCommand.class));
            }
    }

    private void join(Session session, JoinPlayerUCommand j) throws IOException, DataAccessException {
        if (j.getGameID() == -1000 | !GameSQL.isFound(j.getGameID())) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as player");
            session.getRemote().sendString(new Gson().toJson(error));
        }

        int userID = AuthSQL.getUserID(j.authToken);
        String username = UserSQL.getUsername(userID);
        ArrayList<String> players = GameSQL.getPlayers(j.getGameID());
        assert !players.isEmpty();

        if (games.containsKey(j.getGameID())) {
            games.get(j.getGameID()).add(session);
        }
        else {
            ArrayList<Session> s = new ArrayList<Session>();
            s.add(session);
            games.put(j.getGameID(), s);
        }

        if (j.getPlayerColor() == ChessGame.TeamColor.WHITE & !Objects.equals(players.get(0), username)) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "The player was not joined to the game.");
            session.getRemote().sendString(new Gson().toJson(error));
        }
        else if (j.getPlayerColor() == ChessGame.TeamColor.BLACK & !Objects.equals(players.get(1), username)){
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "The player was not joined to the game.");
            session.getRemote().sendString(new Gson().toJson(error));
        }
        else {
            ArrayList<Game> gamesToCheck = GameSQL.listGames(j.authToken);
            assert gamesToCheck != null;
            for (Game item : gamesToCheck) {
                if (item.getGameID() == j.getGameID()) {
                    if (j.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                        if (item.getWhiteUsername() != null && !Objects.equals(item.getWhiteUsername(), username)) {
                            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as player");
                            session.getRemote().sendString(new Gson().toJson(error));
                        }
                    } else {
                        if (item.getBlackUsername() != null && !Objects.equals(item.getBlackUsername(), username)) {
                            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as player");
                            session.getRemote().sendString(new Gson().toJson(error));
                        }
                    }
                }
            }
            boolean foundAuth = AuthSQL.isFound(j.authToken);
            if (j.getPlayerColor() == null | !foundAuth) {
                ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as player");
                session.getRemote().sendString(new Gson().toJson(error));
            }
            else {
                String message = String.format("A player has joined the game as %s", j.getPlayerColor());
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                for (Session value : games.get(j.getGameID())) {
                    if (value != session) {
                        value.getRemote().sendString(new Gson().toJson(notification));
                    }
                    else {
                        LoadGameSMessage game = new LoadGameSMessage(ServerMessage.ServerMessageType.LOAD_GAME, Objects.requireNonNull(GameSQL.getBoard(j.getGameID())).getGame());
                        session.getRemote().sendString(new Gson().toJson(game));
                    }
                }
            }
        }
   }
    private void observe(Session session, JoinObserverUCommand j) throws DataAccessException, IOException {
        if (j.getGameID() == -1000 | !GameSQL.isFound(j.getGameID())) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as observer.");
            session.getRemote().sendString(new Gson().toJson(error));
        }
        if (games.containsKey(j.getGameID())) {
            games.get(j.getGameID()).add(session);
        }
        else {
            ArrayList<Session> s = new ArrayList<Session>();
            s.add(session);
            games.put(j.getGameID(), s);
        }

        String message = "A player has joined the game as an observer";
        for (Session value : games.get(j.getGameID())) {
            if (value != session) {
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
            else {
                LoadGameSMessage game = new LoadGameSMessage(ServerMessage.ServerMessageType.LOAD_GAME, Objects.requireNonNull(GameSQL.getBoard(j.getGameID())).getGame());
                session.getRemote().sendString(new Gson().toJson(game));
            }
        }
    }
    private void move(Session session, String m) throws IOException, DataAccessException, InvalidMoveException {
        var builder = new GsonBuilder();
        builder.registerTypeAdapter(ChessMove.class, new ChessMoveAdapter());
        builder.registerTypeAdapter(ChessPosition.class, new ChessPositionAdapter());
        builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
        MakeMoveUCommand j = builder.create().fromJson(m, MakeMoveUCommand.class);
        ChessMove moveToMake = j.getMove();
        ChessPosition startPosition = j.getMove().getStartPosition();
        ChessPosition endPosition = j.getMove().getEndPosition();

        ((ChessPositionE)j.getMove().getStartPosition()).setRow(startPosition.getRow()-1);
        ((ChessPositionE)j.getMove().getStartPosition()).setCol(startPosition.getColumn()-1);
        ((ChessPositionE)j.getMove().getEndPosition()).setRow(endPosition.getRow()-1);
        ((ChessPositionE)j.getMove().getEndPosition()).setCol(endPosition.getColumn()-1);

        ChessPiece pieceToMove = GameSQL.getBoard(j.getGameID()).getGame().getBoard().getPiece(startPosition);
        if (pieceToMove.getTeamColor() != teamTurn) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: invalid move.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        if (j.getGameID() == -1) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: a player has already resigned from this game.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        ChessGame chessgame = GameSQL.getBoard(j.getGameID()).getGame();
        String usernameTryingToMakeMove = UserSQL.getUsername(AuthSQL.getUserID(j.authToken));
        //teamTurn = chessgame.getTeamTurn();
        ChessGame.TeamColor colorTryingToMakeMove = ChessGame.TeamColor.WHITE;
        if (usernameTryingToMakeMove == GameSQL.getBoard(j.getGameID()).getBlackUsername()) {
            colorTryingToMakeMove = ChessGame.TeamColor.BLACK;
        }
        Collection<ChessMove> validMoves = chessgame.validMoves(startPosition);
        if (!validMoves.contains(j.getMove()) | colorTryingToMakeMove != teamTurn) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: invalid move.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        chessgame.makeMove(j.getMove());
        String message = String.format("%s has made a move.", usernameTryingToMakeMove);
        LoadGameSMessage game = new LoadGameSMessage(ServerMessage.ServerMessageType.LOAD_GAME, Objects.requireNonNull(GameSQL.getBoard(j.getGameID())).getGame());
        for (Session value : games.get(j.getGameID())) {
            if (value != session) {
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
                value.getRemote().sendString(new Gson().toJson(game));
            }
            else {
                session.getRemote().sendString(new Gson().toJson(game));
            }
        }

        //Update who's turn it is in game
        if (teamTurn == ChessGame.TeamColor.WHITE) {
            teamTurn = ChessGame.TeamColor.BLACK;
        }
        else {
            teamTurn = ChessGame.TeamColor.WHITE;
        }
    }
    private void leave(Session session, UserGameCommand command) {
        //FIXME
    }
    private void resign(Session session, ResignUCommand j) throws IOException, DataAccessException {
        ArrayList<String> players = GameSQL.getPlayers(j.getGameID());
        if (!players.contains(UserSQL.getUsername(AuthSQL.getUserID(j.authToken)))) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: can't resign as observer.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        String message = "A player has resigned from the game.";
        for (Session value : games.get(j.getGameID())) {
            if (value != session) {
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
            else {
                message = "You have resigned from the game.";
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
        }
        GameSQL.getBoard(j.getGameID()).setGameID(-1);
    }
}

