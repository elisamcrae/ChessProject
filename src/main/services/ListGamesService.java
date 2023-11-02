package services;

import dataAccess.DataAccessException;
import dataAccess.GameSQL;
import model.Game;
import responses.ListGamesResponse;

import java.util.ArrayList;

/**
 * Gives a list of all games, both past and current.
 */
public class ListGamesService {

    /**
     * Lists all the past and previous games that have been played.
     *
     * @param auth  the authentication token of the user making the request
     * @return  the array list of all chess games that are in the database in the form of a list games response object
     */
    public ListGamesResponse listGames(String auth) {
        ListGamesResponse r = new ListGamesResponse();
        ArrayList<Game> games = null;
        try {
            games = GameSQL.listGames(auth);
            if (games != null) {
                r.setGames(games);
                r.setMessage("Success!");
            }
            else {
                r.setMessage("Error: unauthorized");
            }
        } catch (DataAccessException e) {
            r.setMessage("Error: unauthorized");
        }
        return r;
    }
}
