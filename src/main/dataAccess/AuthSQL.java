package dataAccess;

import model.AuthToken;

import java.sql.SQLException;
import java.util.Objects;

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
            preparedStatement.setString(2, String.valueOf(u.getUserID()));
            preparedStatement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.toString());
        } finally {
            db.returnConnection(conn);
        }
    };

    public static boolean delete(String auth) throws DataAccessException, SQLException {
//        if (authDB.isEmpty()) {
//            return false;
//        }
//        for(int i = 0; i < authDB.size(); ++i) {
//            if (Objects.equals(authDB.get(i).getAuthToken(), auth)) {
//                authDB.remove(i);
//                return true;
//            }
//        }
//        return false;
        var conn = db.getConnection();
        try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE token=?")) {
            preparedStatement.setString(1, auth);
            preparedStatement.executeUpdate();
            db.returnConnection(conn);
            return true;
        }

    }

    /**
     * Attempts to find the authentication string within the database.
     *
     * @param auth  the authentication string correlating to the authentication token object in the database
     * @return  true if the auth string was found, otherwise returns false
     * @throws DataAccessException  if the database cannot be located
     */
    public static boolean isFound(String auth) throws DataAccessException {
//        if (authDB.isEmpty()) {
//            return false;
//        }
//        for (AuthToken authToken : authDB) {
//            if (Objects.equals(authToken.getAuthToken(), auth)) {
//                return true;
//            }
//        }
//        return false;
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

    /**
     * Returns the userID correlating to an authentication token.
     *
     * @param auth  the string authentication correlating to the authentication token object in the database
     * @return  the int userID, otherwise returns -10000
     */
    static int getUserID(String auth) throws DataAccessException {
//        for (AuthToken authToken : authDB) {
//            if (Objects.equals(authToken.getAuthToken(), auth)) {
//                return authToken.getUserID();
//            }
//        }
//        return -10000;
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
