package Communication;

import webSocketMessages.serverMessages.ServerMessage;

public class NotificationSMessage extends ServerMessage {
    //public String notification;
    public NotificationSMessage(ServerMessageType type, String notification) {
        super(type, notification);
        //this.notification = notification;
    }
}
