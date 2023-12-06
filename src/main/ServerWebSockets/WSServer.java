package ServerWebSockets;

import Communication.*;
import chess.*;
import com.google.gson.*;
import dataAccess.*;
import model.Game;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
@WebSocket
public class WSServer {
    private ConcurrentHashMap<Integer, ArrayList<Session>> games = new ConcurrentHashMap<>();

    @OnWebSocketMessage
    public void onMessage(org.eclipse.jetty.websocket.api.Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayerUCommand.class));
                case JOIN_OBSERVER -> observe(session, new Gson().fromJson(message, JoinObserverUCommand.class));
                case MAKE_MOVE -> move(session, message);
                case LEAVE -> leave(session, new Gson().fromJson(message, LeaveUCommand.class));
                case RESIGN -> resign(session, new Gson().fromJson(message, ResignUCommand.class));
            }
    }
    private void join(Session session, JoinPlayerUCommand j) throws IOException, DataAccessException {
        if (j.getGameID() == -1000 | !GameSQL.isFound(j.getGameID()) | !AuthSQL.isFound(j.authToken)) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as player");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        int userID = AuthSQL.getUserID(j.authToken);
        String username = UserSQL.getUsername(userID);
        ArrayList<String> players = GameSQL.getPlayers(j.getGameID());
        assert !players.isEmpty();
        String auth = j.authToken;

        if (games.containsKey(j.getGameID())) {
            games.get(j.getGameID()).add(session);
        }
        else {
            ArrayList<Session> s = new ArrayList<Session>();
            s.add(session);
            games.put(j.getGameID(), s);
        }
//        if (sessionList.contains(username)) {
//            assert username != null;
//            sessionList.remove(username);
//        }
//        sessionList.put(username, session);

        //Check for illegal join cases
        if (j.getPlayerColor() == ChessGame.TeamColor.WHITE & !Objects.equals(players.get(0), username)) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "The player was not joined to the game.");
            session.getRemote().sendString(new Gson().toJson(error));
        }
        else if (j.getPlayerColor() == ChessGame.TeamColor.BLACK & !Objects.equals(players.get(1), username)){
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "The player was not joined to the game.");
            session.getRemote().sendString(new Gson().toJson(error));
        }
        else {
            ArrayList<Game> gamesToCheck = GameSQL.listGames(auth);
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
                //Add user to the game and notify others
                //String playerUsername = UserSQL.getUsername(AuthSQL.getUserID(j.authToken)); // CHECK THIS OUT
                //String message = String.format("%s has joined the game as %s", username, j.getPlayerColor());
                String message = username + " has joined the game as " + j.getPlayerColor();
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                for (Session value : games.get(j.getGameID())) {
                    if (value != session) {
                        value.getRemote().sendString(new Gson().toJson(notification));
                    } else {
                        LoadGameSMessage game = new LoadGameSMessage(ServerMessage.ServerMessageType.LOAD_GAME, Objects.requireNonNull(GameSQL.getBoard(j.getGameID())).getGame());
                        session.getRemote().sendString(new Gson().toJson(game));
                    }
                }
            }
        }
   }
    private void observe(Session session, JoinObserverUCommand j) throws DataAccessException, IOException {
        if (j.getGameID() == -1000 | !GameSQL.isFound(j.getGameID())/* | AuthSQL.getUserID(j.authToken) == -10000*/) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: could not add user as observer.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        if (games.containsKey(j.getGameID())) {
            games.get(j.getGameID()).add(session);
        }
        else {
            ArrayList<Session> s = new ArrayList<Session>();
            s.add(session);
            games.put(j.getGameID(), s);
        }
        int userID = AuthSQL.getUserID(j.authToken);
        String username = UserSQL.getUsername(userID);
//        if (sessionList.contains(username)) {
//            assert username != null;
//            sessionList.remove(username);
//        }
//        sessionList.put(username, session);

        //Now that all the error checks have passed, add user as observer and notify others
        //String playerUsername = UserSQL.getUsername(AuthSQL.getUserID(j.authToken));
        //String message = String.format("%s has joined the game as an observer", username);
        String message = username + " has joined the game as an observer";
        for (Session value : games.get(j.getGameID())) {
        //for (Session value : sessionList.values()) {
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

        //Create chessPosition objects
        ChessPosition startPosition = j.getMove().getStartPosition();
        ChessPosition endPosition = j.getMove().getEndPosition();
        ((ChessPositionE)j.getMove().getStartPosition()).setRow(startPosition.getRow()-1);
        ((ChessPositionE)j.getMove().getStartPosition()).setCol(startPosition.getColumn()-1);
        ((ChessPositionE)j.getMove().getEndPosition()).setRow(endPosition.getRow()-1);
        ((ChessPositionE)j.getMove().getEndPosition()).setCol(endPosition.getColumn()-1);

        ChessPiece pieceToMove = GameSQL.getBoard(j.getGameID()).getGame().getBoard().getPiece(startPosition);
        ChessGame chessgame = GameSQL.getBoard(j.getGameID()).getGame();
        //private ConcurrentHashMap<String, Session> sessionList = new ConcurrentHashMap<>();
        ChessGame.TeamColor teamTurn = chessgame.getTeamTurn();
        String usernameTryingToMakeMove = UserSQL.getUsername(AuthSQL.getUserID(j.authToken));
        ChessGame.TeamColor colorTryingToMakeMove = ChessGame.TeamColor.WHITE;
        ChessGame.TeamColor otherColor = ChessGame.TeamColor.BLACK;
        ArrayList<String> players = GameSQL.getPlayers(j.getGameID());
        if (Objects.equals(usernameTryingToMakeMove, Objects.requireNonNull(players.get(1)))) {
            colorTryingToMakeMove = ChessGame.TeamColor.BLACK;
            otherColor = ChessGame.TeamColor.WHITE;
        }
        //Check for errors
        if (pieceToMove == null) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: no piece at that position.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        if (pieceToMove.getTeamColor() != teamTurn | pieceToMove.getTeamColor() != colorTryingToMakeMove) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: wrong turn.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        if (j.getGameID() == -1) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: a player has already resigned from this game.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        if (!players.contains(usernameTryingToMakeMove)) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: observers cannot make moves.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        Collection<ChessMove> validMoves = chessgame.validMoves(startPosition);
        if (!validMoves.contains(j.getMove()) | colorTryingToMakeMove != teamTurn) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: invalid move.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }

        //Now that illegal checks have passed, make move and notify other players/observers
        chessgame.makeMove(j.getMove());
        Game g = GameSQL.getBoard(j.getGameID());
        g.setGame((ChessGameE)chessgame);
        GameSQL.updateGame(j.getGameID(), g);
        String message = usernameTryingToMakeMove + " has made a move.";
        LoadGameSMessage game = new LoadGameSMessage(ServerMessage.ServerMessageType.LOAD_GAME, Objects.requireNonNull(chessgame));
        for (Session value : games.get(j.getGameID())) {
        //for (Session value : sessionList.values()) {
            if (value != session) {
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
                value.getRemote().sendString(new Gson().toJson(game));
            }
            else {
                session.getRemote().sendString(new Gson().toJson(game));
            }
        }
        //Update whose turn it is in game
//        if (teamTurn == ChessGame.TeamColor.WHITE) {
//            teamTurn = ChessGame.TeamColor.BLACK;
//        }
//        else {
//            teamTurn = ChessGame.TeamColor.WHITE;
//        }

        //Check if the move resulted in a check or checkmate; if so, send notifications
        boolean isInCheckOrCheckmate = false;
        if (chessgame.isInCheck(otherColor)) {
            message = usernameTryingToMakeMove + " has put his opponent in check.";
            isInCheckOrCheckmate = true;
        }
        if (chessgame.isInCheckmate(otherColor)) {
            message = usernameTryingToMakeMove + " has put his opponent in checkmate.";
            isInCheckOrCheckmate = true;
        }
        if (isInCheckOrCheckmate) {
            for (Session value : games.get(j.getGameID())) {
            //for (Session value : sessionList.values()) {
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
        }
    }
    private void leave(Session session, LeaveUCommand j) throws IOException, DataAccessException {
        int userID = AuthSQL.getUserID(j.authToken);
        String playerUsername = UserSQL.getUsername(userID);
        String message = playerUsername + " has left the game.";
        for (Session value : games.get(j.getGameID())) {
        //for (Session value : sessionList.values()) {
            if (value != session) {
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
        }
        GameSQL.getBoard(j.getGameID()).setGameID(-1);
        boolean removed = games.get(j.getGameID()).remove(session);
        //sessionList.remove(playerUsername);
    }
    private void resign(Session session, ResignUCommand j) throws IOException, DataAccessException {
        ArrayList<String> players = GameSQL.getPlayers(j.getGameID());
        if (players.contains("RESIGNED")) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: another player has already resigned.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        if (!players.contains(UserSQL.getUsername(AuthSQL.getUserID(j.authToken)))) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "ERROR: can't resign as observer.");
            session.getRemote().sendString(new Gson().toJson(error));
            return;
        }
        String playerUsername = UserSQL.getUsername(AuthSQL.getUserID(j.authToken));
        for (Session value : games.get(j.getGameID())) {
        //for (Session value : sessionList.values()) {
            if (value != session) {
                String message = playerUsername + " has resigned from the game.";
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
            else {
                String message = "You have resigned from the game.";
                NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
                value.getRemote().sendString(new Gson().toJson(notification));
            }
        }
        GameSQL.updateWhitePlayer(j.getGameID());
        GameSQL.getBoard(j.getGameID()).setGameID(-1);
    }
}