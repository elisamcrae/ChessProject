package dataAccess;

import chess.ChessBoardE;
import chess.ChessPiece;
import chess.ChessPieceE;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ChessBoardAdapter implements JsonSerializer<ChessBoardE>, JsonDeserializer<ChessBoardE> {
    @Override
    public JsonElement serialize(ChessBoardE src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Assuming ChessBoardE has a method getBoard() that returns a 2D array of ChessPiece
        ChessPiece[][] board = src.getBoard();
        board = (ChessPieceE[][]) board;
        // Serialize the ChessPiece 2D array
        JsonArray boardArray = new JsonArray();
        for (ChessPiece[] row : board) {
            JsonArray rowArray = new JsonArray();
            for (ChessPiece piece : row) {
                rowArray.add(context.serialize(piece));
            }
            boardArray.add(rowArray);
        }

        // Add the serialized array to the JSON object
        jsonObject.add("board", boardArray);

        return jsonObject;
    }

    @Override
    public ChessBoardE deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray boardArray = jsonObject.getAsJsonArray("board");

        if (boardArray != null) {
            int rows = boardArray.size();
            int cols = boardArray.get(0).getAsJsonArray().size();

            ChessPiece[][] board = new ChessPiece[rows][cols];

            for (int i = 0; i < rows; i++) {
                JsonArray rowArray = boardArray.get(i).getAsJsonArray();

                for (int j = 0; j < cols; j++) {
                    JsonElement element = rowArray.get(j);

                    if (!element.isJsonNull()) {
                        board[i][j] = context.deserialize(element, ChessPiece.class);
                    } else {
                        board[i][j] = null;
                    }
                }
            }
            ChessBoardE chessBoard = new ChessBoardE();
            chessBoard.setBoard(board);
            return chessBoard;
        } else {
            throw new JsonParseException("Invalid or missing 'board' field in JSON");
        }
    }
}
