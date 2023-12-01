package ServerWebSockets;

import chess.ChessMove;
import chess.ChessMoveE;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessMoveAdapter implements JsonSerializer<ChessMove>, JsonDeserializer<ChessMove> {
    @Override
    public JsonElement serialize(ChessMove src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("startPosition", context.serialize(src.getStartPosition()));
        jsonObject.add("endPosition", context.serialize(src.getEndPosition()));
        return jsonObject;
    }

    @Override
    public ChessMove deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        ChessPosition startPosition = context.deserialize(jsonObject.get("startPosition"), ChessPosition.class);
        ChessPosition endPosition = context.deserialize(jsonObject.get("endPosition"), ChessPosition.class);
        return new ChessMoveE(startPosition, endPosition);
    }
}
