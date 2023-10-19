package handlers;

import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;


public class LoginHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        LoginRequest req = (LoginRequest)new Gson().fromJson(request.body(), LoginRequest.class);

        LoginService service = new LoginService();
        LoginResponse result = service.login(req);

        return new Gson().toJson(result);
    }
}
