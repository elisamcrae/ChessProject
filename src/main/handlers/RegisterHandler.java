package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;
import services.ClearApplicationService;
import services.LoginService;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        String body = request.body();
        String[] bodyList = body.split("[:\n]");
        String username = bodyList[2];
        String password = bodyList[4];
        String email = bodyList[6];
        boolean successful = false;
        RegisterResponse result = new RegisterResponse();

        try {
            RegisterRequest req = new Gson().fromJson(request.body(), RegisterRequest.class);
            if (req.getEmail() != null & req.getPassword() != null & req.getUsername() != null) {
                RegisterService service = new RegisterService();
                result = service.register(req);
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
