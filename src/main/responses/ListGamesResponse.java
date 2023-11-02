package responses;

import model.Game;

import java.util.ArrayList;
/**
 * HTTP response information when attempting to list all past and current games
 */
public class ListGamesResponse {
    /**
     * The string which will contain the response message, corresponding to the response status code
     */
    private String message;
    /**
     * Array of all games stored in the database
     */
    ArrayList<Game> games;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }
}
