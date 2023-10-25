package handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import services.CreateGameService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class CreateGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) {
        boolean successful = false;
        CreateGameRequest req = new Gson().fromJson(request.body(), CreateGameRequest.class);

        CreateGameResponse result = new CreateGameResponse();
        String authorizationValue = request.headers("Authorization");
        req.setAuthToken(authorizationValue);

        try {
            if (req.getGameName() != null) {
                CreateGameService game = new CreateGameService();
                result = game.createGame(req);

                if (Objects.equals(result.getMessage(), "Success!")) {
                    successful = true;
                    response.status(HttpStatus.OK_200);
                }
            }
            if (!successful & Objects.equals(result.getMessage(), "Error: unauthorized")) {
                response.status(HttpStatus.UNAUTHORIZED_401);
            }
            if (req.getAuthToken() == null| req.getGameName() == null) {
                response.status(HttpStatus.BAD_REQUEST_400);
                result.setMessage("Error: bad request");
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        response.body(new Gson().toJson(result, CreateGameResponse.class));
        return response.body();
    }
}
