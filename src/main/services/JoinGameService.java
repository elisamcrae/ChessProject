package services;

import dataAccess.AuthSQL;
import dataAccess.DataAccessException;
import dataAccess.GameSQL;
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
     * If the request has no player color specified, the user will be added as an observer to the game.
     *
     * @param r the HTTP request to join a game
     * @return  the HTTP response to the request to join a game
     */
    public JoinGameResponse joinGame(JoinGameRequest r) {
        JoinGameResponse response = new JoinGameResponse();
        try {
            if (!AuthSQL.isFound(r.getAuthToken())) {
                response.setMessage("Error: unauthorized");
            }
            else if (!GameSQL.isFound(r.getGameID())) {
                response.setMessage("Error: Bad Request");
            }
            else {
                boolean worked = GameSQL.claimSpot(r.getGameID(), r.getPlayerColor(), r.getAuthToken());
                if (worked) {
                    response.setMessage("Success!");
                    //GameSQL.printBoard(r.getGameID());
                    response.setG(GameSQL.getBoard(r.getGameID()));
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
