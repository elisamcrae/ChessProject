package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.Game;
import requests.CreateGameRequest;
import responses.CreateGameResponse;

/**
 * Creates a new chess game
 */
public class CreateGameService {
    /**
     * Service that responds to an HTTP request to create a new chess game.
     * Request will include the game name, white username, and black username.
     * A new gameID will be created for the new game.
     * @param r the HTTP request to create a new game
     * @return  the HTTP response to the request to create a new game
     */
    public CreateGameResponse createGame(CreateGameRequest r) {
        CreateGameResponse response = new CreateGameResponse();
        try {
            boolean worked = AuthDAO.isFound(r.getAuthToken());
            //boolean worked =
            if (worked) {
                Game g = new Game(r.getWhiteUsername(), r.getBlackUsername(), r.getGameName());
                g.setGameID();
                GameDAO.createGame(g, r.getAuthToken());
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
