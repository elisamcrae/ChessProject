package handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import requests.LogoutRequest;
import responses.LogoutResponse;
import services.LogoutService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class LogoutHandler implements Route {
    @Override
    public Object handle(Request request, Response response) {
        LogoutResponse result = new LogoutResponse();
        String authorizationValue = request.headers("Authorization");
        LogoutRequest req = new LogoutRequest();
        req.setAuthToken(authorizationValue);
        try {
            LogoutService l = new LogoutService();
            result = l.logout(req);
            if (Objects.equals(result.getMessage(), "Success!")) {
                response.status(HttpStatus.OK_200);
            }
            else if (Objects.equals(result.getMessage(), "Error: unauthorized")) {
                response.status(HttpStatus.UNAUTHORIZED_401);
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        return new Gson().toJson(result, LogoutResponse.class);
    }
}
