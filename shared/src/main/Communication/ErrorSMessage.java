package Communication;

import webSocketMessages.serverMessages.ServerMessage;

public class ErrorSMessage extends ServerMessage {
    public String errorMessage;
    public ErrorSMessage(ServerMessageType type, String error) {
        super(type, error);
        errorMessage = error;
    }
}
