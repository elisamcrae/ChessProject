package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import requests.JoinGameRequest;
import responses.JoinGameResponse;

/**
 * Verifies that the specified game exists, and, if a color is specified,
 * adds the caller as the requested color to the game.
 * If no color is specified the user is joined as an observer.
 */
public class JoinGameService {

    /**
     * Service to join a game of chess by passing in the corresponding HTTP request.
     * @param r the HTTP request to join a game
     * @return  the HTTP response to the request to join a game
     */
    public JoinGameResponse joinGame(JoinGameRequest r) {
        JoinGameResponse response = new JoinGameResponse();
        try {
            if (!AuthDAO.isFound(r.getAuthToken())) {
                response.setMessage("Error: unauthorized");
            }
            else if (!GameDAO.isFound(r.getGameID())) {
                response.setMessage("Error: unauthorized");
            }
            else {
                boolean worked = GameDAO.claimSpot(r.getGameID(), r.getPlayerColor(), r.getAuthToken());
                if (worked) {
                    response.setMessage("Success!");
                }
                else {
                    response.setMessage("Error: already taken");
                }
            }
        } catch (DataAccessException e) {
            response.setMessage("Error: Cannot fulfill request");
        }
        return response;
    }
}
