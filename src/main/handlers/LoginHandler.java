package handlers;

import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;
import services.LoginService;
import com.google.gson.Gson;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;


public class LoginHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String body = request.body();
        String[] bodyList = body.split("[:\n]");
        String username = bodyList[2];
        String password = bodyList[4];
        boolean successful = false;
        LoginResponse result = new LoginResponse();

        try {
            LoginRequest req = new Gson().fromJson(request.body(), LoginRequest.class);
            if (req.getPassword() != null & req.getUsername() != null) {
                LoginService service = new LoginService();
                result = service.login(req);
                if (Objects.equals(result.getMessage(), "200")) {
                    successful = true;
                }
            }
            if (!successful) {
                response.status(400);
            }
        }
        catch (Exception e) {
            response.status(500);
        }
        //return new Gson().toJson(response);
        return new Gson().toJson(result);
    }
}
