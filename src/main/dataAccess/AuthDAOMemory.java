package dataAccess;

import model.AuthToken;
import model.User;

import java.util.ArrayList;

/**
 * Implementation of AuthDAO when data is stored in memory
 */
public class AuthDAOMemory implements AuthDAO{
    /**
     * Database holding all authentication tokens
     */
    private static ArrayList<AuthToken> authDatabase = new ArrayList<>();
    public static ArrayList<AuthToken> getAuthDatabase() {
        return authDatabase;
    }
}
