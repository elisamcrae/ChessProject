package handlers;

import com.google.gson.Gson;
import model.AuthToken;
import org.eclipse.jetty.http.HttpStatus;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.ClearResponse;
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
import java.util.UUID;

public class RegisterHandler implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        boolean successful = false;
        RegisterResponse result = new RegisterResponse();
        try {
            RegisterRequest req = new Gson().fromJson(request.body(), RegisterRequest.class);

            if (req.getEmail() != null && req.getPassword() != null && req.getUsername() != null) {
                RegisterService service = new RegisterService();
                result = service.register(req);

                if (Objects.equals(result.getMessage(), "Success!")) {
                    successful = true;
                    response.status(HttpStatus.OK_200);
                }
            }
            if (!successful) {
                response.status(400);
                result.setMessage("Error");
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        response.body(new Gson().toJson(result, RegisterResponse.class));
        return response.body();
    }
}
