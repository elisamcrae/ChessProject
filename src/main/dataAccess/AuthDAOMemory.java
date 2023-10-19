package dataAccess;

import model.AuthToken;
import model.User;

import java.util.ArrayList;

/**
 * Implementation of AuthDAO when data is stored in memory
 */
public class AuthDAOMemory implements AuthDAO{
    private static ArrayList<AuthToken> authDatabase = new ArrayList<>();

    @Override
    public void updateAuthToken(AuthToken a) throws DataAccessException {

    }

    @Override
    public void deleteAuthToken(AuthToken a) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {

    }
}
