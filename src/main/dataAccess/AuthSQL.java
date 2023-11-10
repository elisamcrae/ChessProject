package dataAccess;

import model.AuthToken;

import java.sql.SQLException;

public class AuthSQL implements AuthDAO{
    private static Database db = new Database();
    public static void clear() throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("TRUNCATE auth")) {
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    };
    public static void createAuth(AuthToken u) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (token, userID) VALUES(?, ?)")) {
            preparedStatement.setString(1, u.getAuthToken());
            preparedStatement.setInt(2, u.getUserID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    };
    public static boolean delete(String auth) throws DataAccessException, SQLException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE token=?")) {
            preparedStatement.setString(1, auth);
            preparedStatement.executeUpdate();
            db.returnConnection(conn);
            return true;
        }
    }
    public static boolean isFound(String auth) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT userID FROM auth WHERE token=?")) {
            preparedStatement.setString(1, auth);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public static int getUserID(String auth) throws DataAccessException {
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("SELECT userID FROM auth WHERE token=?")) {
            preparedStatement.setString(1, auth);
            try (var rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt("userID");
                }
                return -10000;
            }
        } catch (SQLException e) {
            return -10000;
        }
    }
}
