package dataAccess;

import chess.ChessPiece;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPieceAdapter implements JsonSerializer<ChessPiece>, JsonDeserializer<ChessPiece> {
    @Override
    public JsonElement serialize(ChessPiece src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pieceType", src.getClass().getName());
        jsonObject.add("teamColor", context.serialize(src));
        return jsonObject;
    }

    @Override
    public ChessPiece deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("pieceType").getAsString();
        ChessPieceFactory pieceFactory = new ChessPieceFactory();
        return pieceFactory.createChessPiece(type, jsonObject);
    }
}
