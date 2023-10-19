package services;

import chess.ChessGameE;
import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;

/**
 * Creates a new chess game
 */
public class CreateGame {
    public CreateGame(CreateGameRequest r) {
        createGame(r);
    }

    /**
     * Service that responds to an HTTP request to create a new chess game.
     * Request will include the game name, white username, and black username.
     * A new gameID will be created for the new game.
     * @param r the HTTP request to create a new game
     * @return  the HTTP response to the request to create a new game
     */
    CreateGameResponse createGame(CreateGameRequest r) {

        return null;
    }

}
