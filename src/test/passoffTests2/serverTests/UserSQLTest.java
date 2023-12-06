package passoffTests2.serverTests;

import dataAccess.DataAccessException;
import dataAccess.UserSQL;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserSQLTest {

    @Test
    void createUserPositive() {
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            Assertions.assertTrue(UserSQL.contains(u));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void createUserNegative() {
        //User not in database
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");

            Assertions.assertFalse(UserSQL.contains(u));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUserByUsernamePositive() {
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            User returnedUser = UserSQL.getUserByUsername(u.getUsername(), u.getPassword());
            Assertions.assertEquals(u.getUserID(), returnedUser.getUserID());
            Assertions.assertEquals(u.getPassword(), returnedUser.getPassword());
            Assertions.assertEquals(u.getEmail(), returnedUser.getEmail());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUserByUsernameNegative() {
        //username not in database
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");
            Assertions.assertNull(UserSQL.getUserByUsername(u.getUsername(), u.getPassword()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void clearPositive() {
        try {
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            Assertions.assertTrue(UserSQL.contains(u));
            UserSQL.clear();
            Assertions.assertFalse(UserSQL.contains(u));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void containsPositive() {
        try {
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            Assertions.assertTrue(UserSQL.contains(u));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void containsNegative() {
        //database does not contain the user
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            User p = new User("u", "p", "e");
            Assertions.assertFalse(UserSQL.contains(p));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUsernamePositive() {
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            Assertions.assertEquals(u.getUsername(), UserSQL.getUsername(u.getUserID()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUsernameNegative() {
        //user with that userID not in database
        try {
            UserSQL.clear();
            User u = new User("username", "password", "email");
            UserSQL.createUser(u);
            User p = new User("u", "p", "e");
            Assertions.assertNotEquals(u.getUsername(), UserSQL.getUsername(p.getUserID()));
            Assertions.assertNull(UserSQL.getUsername(p.getUserID()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}