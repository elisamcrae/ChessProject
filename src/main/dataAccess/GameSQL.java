package dataAccess;

import com.google.gson.Gson;
import model.Game;

import java.sql.SQLException;
import java.util.ArrayList;
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
//        if (AuthDAO.isFound(auth)) {
//            gameDB.add(g);
//            return true;
//        }
//        return false;
        else {
            return false;
        }
    }

    public static boolean isFound(int gameID) throws DataAccessException {
//        for (Game game : gameDB) {
//            if (game.getGameID() == gameID) {
//                return true;
//            }
//        }
//        return false;
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
//        int userID = AuthDAO.getUserID(auth);
//        String username = UserDAO.getUsername(userID);
//        if (userID == -10000 | username == null) {
//            return false;
//        }
//        for (Game game : gameDB) {
//            if (game.getGameID() == gameID) {
//                if (Objects.equals(playerColor, "WHITE") && game.getWhiteUsername() == null) {
//                    game.setWhiteUsername(username);
//                    return true;
//                } else if (Objects.equals(playerColor, "BLACK") && game.getBlackUsername() == null) {
//                    game.setBlackUsername(username);
//                    return true;
//                } else if (Objects.equals(playerColor, "") | playerColor == null) {
//                    game.addObserver(username);
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//        return false;

        int userID = AuthSQL.getUserID(auth);
        String username = UserSQL.getUsername(userID);
        if (userID == -10000 | username == null) {
            return false;
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
                        else if (playerColor == null) {
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

    /**
     * Clears all the information within the database by deleting all games.
     */
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

    /**
     * Lists all games in database.
     *
     * @param auth  the string authentication to be verified before returning games
     * @return  game database in the form of an array
     * @throws DataAccessException  if database cannot be located
     */
    public static ArrayList<Game> listGames(String auth) throws DataAccessException {
//        if (AuthDAO.isFound(auth)) {
//            return gameDB;
//        }
//        return null;
        if (!AuthSQL.isFound(auth)) {
            return null;
        }
        ArrayList<Game> gameslist = new ArrayList<Game>();

        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT games FROM game")) {
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    var json = rs.getString("games");
                    Game g = new Gson().fromJson(json, Game.class);
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
}
