package passoffTests2.serverTests;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import model.AuthToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthSQLTest {

    @Test
    void clearPositive() {
        AuthToken a = new AuthToken(1);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            AuthSQL.clear();
            Assertions.assertFalse(AuthSQL.isFound(a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createAuthPositive() {
        try {
            AuthSQL.clear();
            AuthToken a = new AuthToken(1);
            AuthSQL.createAuth(a);
            Assertions.assertNotNull(AuthSQL.isFound(a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void createAuthNegative() {
        //Try finding without anything in auth
        try {
            AuthSQL.clear();
            AuthToken a = new AuthToken(1);
            Assertions.assertFalse(AuthSQL.isFound(a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deletePositive() {
        AuthToken a = new AuthToken(1);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            AuthSQL.delete(a.getAuthToken());
            Assertions.assertFalse(AuthSQL.isFound(a.getAuthToken()));
        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deleteNegative() {
        AuthToken a = new AuthToken(1);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            AuthToken b = new AuthToken(2);
            AuthSQL.delete(b.getAuthToken());

            Assertions.assertTrue(AuthSQL.isFound(a.getAuthToken()));
            Assertions.assertFalse(AuthSQL.isFound(b.getAuthToken()));

        } catch (DataAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isFoundPositive() {
        AuthToken a = new AuthToken(1);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            Assertions.assertTrue(AuthSQL.isFound(a.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isFoundNegative() {
        //Try to find auth c when it isn't in database
        AuthToken a = new AuthToken(1);
        AuthToken b = new AuthToken(2);
        AuthToken c = new AuthToken(3);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            AuthSQL.createAuth(b);
            Assertions.assertFalse(AuthSQL.isFound(c.getAuthToken()));
            Assertions.assertTrue(AuthSQL.isFound(a.getAuthToken()));
            Assertions.assertTrue(AuthSQL.isFound(b.getAuthToken()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUserIDPositive() {
        AuthToken a = new AuthToken(1);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            int returnedUserID = AuthSQL.getUserID(a.getAuthToken());
            Assertions.assertEquals(returnedUserID, a.getUserID());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUserIDNegative() {
        //Try to look up a user ID with auth that isn't in database
        AuthToken a = new AuthToken(1);
        AuthToken b = new AuthToken(2);
        try {
            AuthSQL.clear();
            AuthSQL.createAuth(a);
            int returnedUserID = AuthSQL.getUserID(b.getAuthToken());
            Assertions.assertNotEquals(returnedUserID, a.getUserID());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}