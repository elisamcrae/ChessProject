package handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import responses.ListGamesResponse;
import services.ListGamesService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class ListGamesHandler implements Route {
    @Override
    public Object handle(Request request, Response response) {
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
