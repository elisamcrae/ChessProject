package ServerWebSockets;

import chess.ChessPosition;
import chess.ChessPositionE;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessPositionAdapter implements JsonSerializer<ChessPosition>, JsonDeserializer<ChessPosition> {

    @Override
    public JsonElement serialize(ChessPosition src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("row", src.getRow());
        jsonObject.addProperty("col", src.getColumn());
        return jsonObject;
    }

    @Override
    public ChessPosition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int row = jsonObject.get("row").getAsInt();
        int column = jsonObject.get("col").getAsInt();
        return new ChessPositionE(row, column);
    }
}
