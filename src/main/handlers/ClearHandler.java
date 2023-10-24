package handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import requests.LoginRequest;
import responses.ClearResponse;
import responses.LoginResponse;
import responses.RegisterResponse;
import services.ClearApplicationService;
import services.LoginService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;

public class ClearHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        boolean successful = false;
        ClearResponse result = new ClearResponse();
        try {
            ClearApplicationService clear = new ClearApplicationService();
            clear.clearApplication();
            successful = true;
            result.setMessage("Success!");
            response.status(HttpStatus.OK_200);
            if (!successful) {
                response.status(400);
                result.setMessage("Error");
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        return new Gson().toJson(result, ClearResponse.class);
    }
}
