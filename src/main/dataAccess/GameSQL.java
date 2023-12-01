package dataAccess;

import chess.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.protobuf.Internal;
import model.Game;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class GameSQL implements GameDAO{
    private static Database db = new Database();

    public static boolean createGame(Game g, String auth) throws DataAccessException {
        if (AuthSQL.isFound(auth)) {
            Boolean toReturn = false;
            var conn = db.getConnection();
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (whitePlayer, blackPlayer, gameName, games, gameID) VALUES(?, ?, ?, ?, ?)")) {
                preparedStatement.setString(1, g.getWhiteUsername());
                preparedStatement.setString(2, g.getBlackUsername());
                preparedStatement.setString(3, g.getGameName());

                var json = new Gson().toJson(g);
                preparedStatement.setString(4, json);
                preparedStatement.setInt(5, g.getGameID());

                preparedStatement.execute();
                toReturn = true;
            } catch (SQLException ex) {
                throw new DataAccessException(ex.toString());
            } finally {
                db.returnConnection(conn);
            }
            return toReturn;
        }
        else {
            return false;
        }
    }

    public static boolean isFound(int gameID) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT gameName FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            db.returnConnection(conn);
        }
    }

    public static boolean claimSpot(int gameID, String playerColor, String auth) throws DataAccessException {
        int userID = AuthSQL.getUserID(auth);
        String username = UserSQL.getUsername(userID);
        if (userID == -10000 | username == null) {
            return false;
        }
        if (Objects.equals(playerColor, "null")) {
            playerColor = "";
        }

        var conn = db.getConnection();
        Boolean toReturn = true;
        try (var preparedStatement = conn.prepareStatement("SELECT gameID, whitePlayer, blackPlayer, observers FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var id = rs.getInt("gameID");
                    var whiteP = rs.getString("whitePlayer");
                    var blackP = rs.getString("blackPlayer");
                    var obs = rs.getString("observers");

                    if (id == gameID) {
                        if (Objects.equals(playerColor, "WHITE") && whiteP == null) {
                            try (var preparedStatement2 = conn.prepareStatement("UPDATE game SET whitePlayer=? WHERE gameID=?")) {
                                preparedStatement2.setString(1, username);
                                preparedStatement2.setInt(2, gameID);

                                preparedStatement2.executeUpdate();
                            }
                        }
                        else if (Objects.equals(playerColor, "BLACK") && blackP == null) {
                            try (var preparedStatement2 = conn.prepareStatement("UPDATE game SET blackPlayer=? WHERE gameID=?")) {
                                preparedStatement2.setString(1, username);
                                preparedStatement2.setInt(2, gameID);

                                preparedStatement2.executeUpdate();
                            }
                        }
                        else if (playerColor == null | Objects.equals(playerColor, "")) {
                            try (var preparedStatement2 = conn.prepareStatement("UPDATE game SET observers=? WHERE gameID=?")) {
                                preparedStatement2.setString(1, obs + "," + username);
                                preparedStatement2.setInt(2, gameID);

                                preparedStatement2.executeUpdate();
                            }
                        }
                        else {
                            toReturn = false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            toReturn = false;
        } finally {
            db.returnConnection(conn);
        }
        return toReturn;
    }

    public static void clear() throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("TRUNCATE game")) {
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    };

    public static ArrayList<Game> listGames(String auth) throws DataAccessException {
        if (!AuthSQL.isFound(auth)) {
            return null;
        }
        ArrayList<Game> gameslist = new ArrayList<Game>();

        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT games, whitePlayer, blackPlayer FROM game")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var json = rs.getString("games");
                    var white = rs.getString("whitePlayer");
                    var black = rs.getString("blackPlayer");
                    //Game g = new Gson().fromJson(json, Game.class);
                    var builder = new GsonBuilder();
                    builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
                    builder.registerTypeAdapter(ChessBoardE.class, new ChessBoardAdapter());
                    Game g = builder.create().fromJson(json, Game.class);
                    g.setWhiteUsername(white);
                    g.setBlackUsername(black);
                    gameslist.add(g);
                }
            }
        } catch (SQLException e) {
            gameslist = gameslist;
        } finally {
            db.returnConnection(conn);
        }
        return gameslist;
    }


    public static Game getBoard(int gameID) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT games FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var json = rs.getString("games");
                    var builder = new GsonBuilder();
                    builder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
                    builder.registerTypeAdapter(ChessBoardE.class, new ChessBoardAdapter());
                    Game g = builder.create().fromJson(json, Game.class);
                    db.returnConnection(conn);
                    return g;
                }
            }
        } catch (SQLException e) {
            return null;
        } finally {
            db.returnConnection(conn);
        }
        return null;
    }
    public static void printBoard(int gameID) {
        try {
            Game g = getBoard(gameID);
            assert g != null;
            ChessGame myGame = g.getGame();
            ChessBoard board = myGame.getBoard();

            //PRINT ACTUAL BOARD
            for (int i = 0; i < 8; ++i) {
                System.out.println("\n----------------");
                for (int j = 0; j < 8; ++j) {
                    System.out.print("|");
                    System.out.print(board.getPiece(new ChessPositionE(i+1, j+1)));
                    System.out.print("|");
                }
            }

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> getPlayers(int gameID) throws DataAccessException {
        var conn = db.getConnection();
        ArrayList<String> toReturn = new ArrayList<>();

        try (var preparedStatement = conn.prepareStatement("SELECT whitePlayer, blackPlayer FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String white = rs.getString("whitePlayer");
                    String black = rs.getString("blackPlayer");
                    toReturn.add(white);
                    toReturn.add(black);
                }
            }
        } catch (SQLException e) {
        } finally {
            db.returnConnection(conn);
        }
        return toReturn;
    }

    public static ArrayList<String> getObservers(int gameID) throws DataAccessException {
        var conn = db.getConnection();
        ArrayList<String> toReturn = new ArrayList<>();

        try (var preparedStatement = conn.prepareStatement("SELECT observers FROM game WHERE gameID=?")) {
            preparedStatement.setInt(1, gameID);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    String observers = rs.getString("observers");
                    toReturn.add(observers);
                }
            }
        } catch (SQLException e) {
        } finally {
            db.returnConnection(conn);
        }
        return toReturn;
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





