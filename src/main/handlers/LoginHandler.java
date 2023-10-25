package handlers;

import org.eclipse.jetty.http.HttpStatus;
import requests.LoginRequest;
import responses.LoginResponse;
import services.LoginService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;


public class LoginHandler implements Route {
    @Override
    public Object handle(Request request, Response response) {
        boolean successful = false;
        LoginResponse result = new LoginResponse();
        try {
            LoginRequest req = new Gson().fromJson(request.body(), LoginRequest.class);

            if (req.getPassword() != null && req.getUsername() != null) {
                LoginService service = new LoginService();
                result = service.login(req);

                if (Objects.equals(result.getMessage(), "Success!")) {
                    successful = true;
                    response.status(HttpStatus.OK_200);
                }
            }
            if (req.getPassword() == null | req.getUsername() == null) {
                response.status(HttpStatus.UNAUTHORIZED_401);
                result.setMessage("Error: unauthorized");
            }
            else if (!successful && Objects.equals(result.getMessage(), "Error: unauthorized")) {
                response.status(HttpStatus.UNAUTHORIZED_401);
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
            result.setMessage("Error: Cannot complete request");
        }
        response.body(new Gson().toJson(result, LoginResponse.class));
        return response.body();
    }
}
