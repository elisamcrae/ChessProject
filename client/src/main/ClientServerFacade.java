import chess.*;
import com.google.gson.*;
import model.Game;
import responses.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ClientServerFacade {
    private static String myURL = "http://localhost:8080";
    private static String authToken = "";
    private static Boolean toUseAuthToken = false;
    public static <T> T main(ArrayList<String> args, Class<T> responseType) throws Exception {
        if (args.size() >= 2) {
            var method = args.get(0);
            var url = args.get(1);
            var body = args.size() == 3 ? args.get(2) : "";
            if (responseType == LogoutResponse.class | responseType == CreateGameResponse.class | responseType == JoinGameResponse.class | responseType == ListGamesResponse.class) {
                toUseAuthToken = true;
            }
            else {
                toUseAuthToken = false;
                authToken = "";
            }
            HttpURLConnection http = sendRequest(url, method, body);
            T x = receiveResponse(http, responseType);
            return x;
        } else {
            return null;
        }
    }

    private static HttpURLConnection sendRequest(String path, String method, String body) throws URISyntaxException, IOException {
        URL url = (new URI(myURL + path)).toURL();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod(method);
        if (toUseAuthToken) {
            http.setRequestProperty("Authorization", authToken);
        }
        writeRequestBody(body, http);
        http.connect();
        //System.out.printf("= Request =========\n[%s] %s\n\n%s\n\n", method, url, body);
        return http;
    }

    private static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    private static <T> T receiveResponse(HttpURLConnection http, Class<T> responseType) throws IOException {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();
        T x = readResponseBody(http, responseType);
        return x;
    }

    private static <T> T readResponseBody(HttpURLConnection http, Class<T> responseType) throws IOException {
        T responseBody = null;
        InputStreamReader inputStreamReader;
        try (InputStream respBody = http.getInputStream()) {
            inputStreamReader = new InputStreamReader(respBody);
            var builder = new GsonBuilder();
            builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
            builder.registerTypeAdapter(ChessBoardE.class, new ChessBoardAdapter());
            responseBody = builder.create().fromJson(inputStreamReader, responseType);
        }
        return responseBody;
    }

    public RegisterResponse register(ArrayList<String> params) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("POST");
        p.add("/user");
        p.add("{\"username\": \"" + params.get(0) + "\", \"password\": \"" + params.get(1) + "\", \"email\": \"" + params.get(2) + "\"}");
        return main(p, RegisterResponse.class);
    }

    public LoginResponse login(ArrayList<String> params) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("POST");
        p.add("/session");
        p.add("{\"username\": \"" + params.get(0) + "\", \"password\": \"" + params.get(1) + "\"}");
        return main(p, LoginResponse.class);
    }

    public void logout(String auth) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("DELETE");
        p.add("/session");
        authToken = auth;
        main(p, LogoutResponse.class);
    }

    public CreateGameResponse create(String name, String auth) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("POST");
        p.add("/game");
        p.add("{\"gameName\": \"" + name + "\"}");
        authToken = auth;
        return main(p, CreateGameResponse.class);
    }

    public JoinGameResponse join(int gameID, String color, String auth) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("PUT");
        p.add("/game");
        p.add("{\"playerColor\":\"" + color + "\", \"gameID\":" + gameID + "}");
        authToken = auth;
        return main(p, JoinGameResponse.class);
    }

    public JoinGameResponse join(int gameID, String auth) throws Exception {
        return join(gameID, null, auth);
    }

    public ListGamesResponse list(String auth) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("GET");
        p.add("/game");
        authToken = auth;
        return main(p, ListGamesResponse.class);
    }
}

class ChessPieceAdapter implements JsonSerializer<ChessPiece>, JsonDeserializer<ChessPiece> {
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
//        String newString = type.toLowerCase();
//        newString = newString.substring(0,1).toUpperCase() + newString.substring(1);
//        newString = "chess." + newString;
//        JsonElement data = jsonObject.get("data");
//        try {
//            return context.deserialize(data, Class.forName(newString));
//        } catch (ClassNotFoundException e) {
//            throw new JsonParseException("Unknown element type: " + newString, e);
//        }
        ChessPieceFactory pieceFactory = new ChessPieceFactory();
        ChessPiece x = pieceFactory.createChessPiece(type, jsonObject);
        return x;
    }
}

class ChessBoardAdapter implements JsonSerializer<ChessBoardE>, JsonDeserializer<ChessBoardE> {
    @Override
    public JsonElement serialize(ChessBoardE src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        // Assuming ChessBoardE has a method getBoard() that returns a 2D array of ChessPiece
        ChessPiece[][] board = src.getBoard();
        board = (ChessPieceE[][])board;
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

class ChessPieceFactory {
    public ChessPiece createChessPiece(String type, JsonObject jsonObject) {
        return switch (type) {
            case "ROOK" -> {
                if (jsonObject.get("teamColor").getAsString().equals("WHITE")) {
                    yield new Rook(ChessGame.TeamColor.WHITE);
                }
                yield new Rook(ChessGame.TeamColor.BLACK);
            }
            case "KNIGHT" -> {
                if (Objects.equals(jsonObject.get("teamColor").getAsString(), "WHITE")) {
                    yield new Knight(ChessGame.TeamColor.WHITE);
                }
                yield new Knight(ChessGame.TeamColor.BLACK);
            }
            case "PAWN" -> {
                if (Objects.equals(jsonObject.get("teamColor").getAsString(), "WHITE")) {
                    yield new Pawn(ChessGame.TeamColor.WHITE);
                }
                yield new Pawn(ChessGame.TeamColor.BLACK);
            }
            case "QUEEN" -> {
                if (Objects.equals(jsonObject.get("teamColor").getAsString(), "WHITE")) {
                    yield new Queen(ChessGame.TeamColor.WHITE);
                }
                yield new Queen(ChessGame.TeamColor.BLACK);
            }
            case "KING" -> {
                if (Objects.equals(jsonObject.get("teamColor").getAsString(), "WHITE")) {
                    yield new King(ChessGame.TeamColor.WHITE);
                }
                yield new King(ChessGame.TeamColor.BLACK);
            }
            case "BISHOP" -> {
                if (Objects.equals(jsonObject.get("teamColor").getAsString(), "WHITE")) {
                    yield new Bishop(ChessGame.TeamColor.WHITE);
                }
                yield new Bishop(ChessGame.TeamColor.BLACK);
            }
            default -> throw new JsonParseException("Unknown ChessPiece type: " + type);
        };
    }
}
