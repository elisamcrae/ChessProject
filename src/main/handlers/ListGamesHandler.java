package handlers;

import chess.ChessGameE;
import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import requests.JoinGameRequest;
import requests.RegisterRequest;
import responses.JoinGameResponse;
import responses.ListGamesResponse;
import responses.RegisterResponse;
import services.JoinGameService;
import services.ListGamesService;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.ArrayList;
import java.util.Objects;

public class ListGamesHandler implements Route {
    //IS THIS RIGHT???
    @Override
    public Object handle(Request request, Response response) throws Exception {
        boolean successful = false;
        ListGamesResponse result = new ListGamesResponse();
        try {
            String authorizationValue = request.headers("Authorization");

            ListGamesService service = new ListGamesService();
            result = service.listGames(authorizationValue);
            if (Objects.equals(result.getMessage(), "Success!")) {
                successful = true;
                response.status(HttpStatus.OK_200);
            }
            if (!successful & Objects.equals(result.getMessage(), "Error: unauthorized")) {
                response.status(HttpStatus.UNAUTHORIZED_401);
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        response.body(new Gson().toJson(result, ListGamesResponse.class));
        return response.body();
    }
}
