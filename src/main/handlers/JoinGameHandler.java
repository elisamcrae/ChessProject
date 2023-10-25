package handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import requests.JoinGameRequest;
import requests.RegisterRequest;
import responses.JoinGameResponse;
import responses.RegisterResponse;
import services.JoinGameService;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class JoinGameHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        boolean successful = false;
        JoinGameResponse result = new JoinGameResponse();
        try {
            JoinGameRequest req = new Gson().fromJson(request.body(), JoinGameRequest.class);
            req.setAuthToken(request.headers("Authorization"));
            JoinGameService service = new JoinGameService();
            result = service.joinGame(req);
            if (Objects.equals(result.getMessage(), "Success!")) {
                successful = true;
                response.status(HttpStatus.OK_200);
            }
            if (!successful & Objects.equals(result.getMessage(), "Error: unauthorized")) {
                response.status(HttpStatus.UNAUTHORIZED_401);
            }
            else if (!successful & Objects.equals(result.getMessage(), "Error: already taken")) {
                response.status(HttpStatus.FORBIDDEN_403);
            }
            else if (!successful & Objects.equals(result.getMessage(), "Error: Bad Request")) {
                response.status(HttpStatus.BAD_REQUEST_400);
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        response.body(new Gson().toJson(result, JoinGameResponse.class));
        return response.body();
    }
}
