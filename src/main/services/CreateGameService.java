package services;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import dataAccess.GameSQL;
import model.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * Creates a new chess game
 */
public class CreateGameService {
    /**
     * Service that responds to an HTTP request to create a new chess game.
     * A new gameID will be created for the new game.
     * The game will be stored in the GameSQL database.
     *
     * @param r the HTTP request to create a new game
     * @return  the HTTP response to the request to create a new game
     */
    public CreateGameResponse createGame(CreateGameRequest r) {
        CreateGameResponse response = new CreateGameResponse();
        try {
            boolean worked = AuthSQL.isFound(r.getAuthToken());
            if (worked) {
                Game g = new Game(r.getWhiteUsername(), r.getBlackUsername(), r.getGameName());
                g.setGameID();
                GameSQL.createGame(g, r.getAuthToken());
                response.setMessage("Success!");
                response.setGameID(g.getGameID());
            }
            else {
                response.setMessage("Error: unauthorized");
            }
            return response;
        }
        catch(DataAccessException e) {
            response.setMessage("Error: Unable to create game");
            return response;
        }
    }
}
