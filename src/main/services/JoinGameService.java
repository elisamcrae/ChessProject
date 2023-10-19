package services;

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
    JoinGameResponse joinGame(JoinGameRequest r) {
        return null;
    }
}
