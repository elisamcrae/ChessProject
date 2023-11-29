package ServerWebSockets;

import Communication.*;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.GameSQL;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;

import static java.sql.DriverManager.getConnection;

@WebSocket
public class WSServer {
    private ArrayList<Session> sessions = new ArrayList<Session>();
    @OnWebSocketMessage
    public void onMessage(org.eclipse.jetty.websocket.api.Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

//        var connection = getConnection(command.authToken, (Properties) session);
//        if (!connections.contains(connection)) {
//            connections.add(connection);
//        }
        if (!sessions.contains(session)) {
            sessions.add(session);
        }
        //if (connection != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayerUCommand.class));
                case JOIN_OBSERVER -> observe(session, command);
                case MAKE_MOVE -> move(session, new Gson().fromJson(message, MakeMoveUCommand.class));
                case LEAVE -> leave(session, command);
                case RESIGN -> resign(session, command);
            }
//        } else {
//            session.getRemote().sendString("unknown user");
//        }
    }

    private void join(Session session, JoinPlayerUCommand j) throws IOException, DataAccessException {
        if (j.getGameID() == -1000) {
            ErrorSMessage error = new ErrorSMessage(ServerMessage.ServerMessageType.ERROR, "The team color you requested is already taken.");
            session.getRemote().sendString(new Gson().toJson(error));
        }
        else {
            String message = String.format("A player has joined the game as %s", j.getPlayerColor());
            //session.getRemote().sendString(message);
            NotificationSMessage notification = new NotificationSMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
            for (Session value : sessions) {
                if (value != session) {
                    value.getRemote().sendString(new Gson().toJson(notification));
                }
                else {
                    LoadGameSMessage game = new LoadGameSMessage(ServerMessage.ServerMessageType.LOAD_GAME, GameSQL.getBoard(j.getGameID()).getGame());
                    session.getRemote().sendString(new Gson().toJson(game));
                }
            }
        }
   }
    private void observe(Session session, UserGameCommand command) {
        //FIXME
    }
    private void move(Session session, MakeMoveUCommand j) {
        //FIXME
    }
    private void leave(Session session, UserGameCommand command) {
        //FIXME
    }
    private void resign(Session session, UserGameCommand command) {
        //FIXME
    }
}
