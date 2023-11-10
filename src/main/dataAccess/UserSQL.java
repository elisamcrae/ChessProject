package dataAccess;

import model.User;

import java.sql.SQLException;
import java.util.Objects;

public class UserSQL implements UserDAO{
    private static Database db = new Database();

    public static void createUser(User u) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email, userID) VALUES(?, ?, ?, ?)")) {
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setString(3, u.getEmail());
            preparedStatement.setInt(4, u.getUserID());

            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    };

//    public static User getUser(AuthToken a) throws DataAccessException {
//        var conn = db.getConnection();
//        int userID = a.getUserID();
//        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE userID=?")) {
//            preparedStatement.setInt(1, userID);
//            try (var rs = preparedStatement.executeQuery()) {
//                var username = rs.getString("username");
//                var password = rs.getString("password");
//                var email = rs.getString("email");
//
//                User newU = new User(username, password, email);
//                db.returnConnection(conn);
//                return newU;
//            }
//        } catch (SQLException e) {
//            db.returnConnection(conn);
//            return null;
//        }
//    }

    public static User getUserByUsername(String username, String password) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT userID, email, password FROM user WHERE username=?")) {
            preparedStatement.setString(1, username);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var email = rs.getString("email");
                var p = rs.getString("password");
                var userID = rs.getInt("userID");
                if (!Objects.equals(p, password)) {
                    db.returnConnection(conn);
                    return null;
                }
                User newU = new User(username, password, email, userID);
                db.returnConnection(conn);
                return newU;
            }
        } catch (SQLException e) {
            db.returnConnection(conn);
            return null;
        }
    }

//    static void deleteUser(User u) throws DataAccessException, SQLException {
//        var conn = db.getConnection();
//        try (var preparedStatement = conn.prepareStatement("DELETE FROM user WHERE username=?")) {
//            preparedStatement.setString(1, u.getUsername());
//            preparedStatement.executeUpdate();
//        }
//        db.returnConnection(conn);
//    }

    public static void clear() throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("TRUNCATE user")) {
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    };

    public static boolean contains(User u) throws DataAccessException {
        Boolean toReturn = true;
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT password, email FROM user WHERE username=?")) {
            preparedStatement.setString(1, u.getUsername());
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var password = rs.getString("password");
                var email = rs.getString("email");
                toReturn = Objects.equals(password, u.getPassword()) & Objects.equals(email, u.getEmail());
            }
        } catch (SQLException e) {
            toReturn = false;
        } finally {
            db.returnConnection(conn);
        }
        return toReturn;
    }

    public static String getUsername(int userID) throws DataAccessException {
        String toReturn = "";
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT username FROM user WHERE userID=?")) {
            preparedStatement.setInt(1, userID);
            try (var rs = preparedStatement.executeQuery()) {
                rs.next();
                var username = rs.getString("username");
                toReturn = username;
            }
        } catch (SQLException e) {
            toReturn = "";
        } finally {
            db.returnConnection(conn);
        }
        if (Objects.equals(toReturn, "")) {
            return null;
        }
        else {
            return toReturn;
        }
    }
}