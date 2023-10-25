package responses;

import model.Game;

import java.util.ArrayList;

public class ListGamesResponse {
    private String message;
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
