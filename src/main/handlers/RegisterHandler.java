package handlers;

import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;
import services.LoginService;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        RegisterRequest req = (RegisterRequest)new Gson().fromJson(request.body(), RegisterRequest.class);

        RegisterService service = new RegisterService();
        RegisterResponse result = service.register(req);

        return new Gson().toJson(result);
    }
}
