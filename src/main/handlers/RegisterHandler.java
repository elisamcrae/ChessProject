package handlers;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import requests.RegisterRequest;
import responses.RegisterResponse;
import services.RegisterService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Objects;

public class RegisterHandler implements Route {
    @Override
    public Object handle(Request request, Response response) {
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
            if (!successful & Objects.equals(result.getMessage(), "Error: already taken")) {
                response.status(HttpStatus.FORBIDDEN_403);
            }
            if (req.getEmail() == null | req.getPassword() == null | req.getUsername() == null) {
                response.status(HttpStatus.BAD_REQUEST_400);
                result.setMessage("Error: bad request");
            }
        }
        catch (Exception e) {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
        response.body(new Gson().toJson(result, RegisterResponse.class));
        return response.body();
    }
}
