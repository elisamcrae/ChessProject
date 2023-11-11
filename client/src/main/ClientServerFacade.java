import com.google.gson.*;
import responses.RegisterResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class ClientServerFacade {
    private static String myURL = "http://localhost:8080";
    public static <T> T main(ArrayList<String> args, Class<T> responseType) throws Exception {
        if (args.size() >= 2) {
            var method = args.get(0);
            var url = args.get(1);
            var body = args.size() == 3 ? args.get(2) : "";

            HttpURLConnection http = sendRequest(url, method, body);
            return receiveResponse(http, responseType);
        } else {
            return null;
        }
    }

    private static HttpURLConnection sendRequest(String path, String method, String body) throws URISyntaxException, IOException {
        URL url = (new URI(myURL + path)).toURL();
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod(method);
        writeRequestBody(body, http);
        http.connect();
        //System.out.printf("= Request =========\n[%s] %s\n\n%s\n\n", method, url, body);
        return http;
    }

    private static void writeRequestBody(String body, HttpURLConnection http) throws IOException {
        if (!body.isEmpty()) {
            http.setDoOutput(true);
            try (var outputStream = http.getOutputStream()) {
                outputStream.write(body.getBytes());
            }
        }
    }

    private static <T> T receiveResponse(HttpURLConnection http, Class<T> responseType) throws IOException {
        var statusCode = http.getResponseCode();
        var statusMessage = http.getResponseMessage();

        return readResponseBody(http, responseType);
    }

    private static <T> T readResponseBody(HttpURLConnection http, Class<T> responseType) throws IOException {
        T responseBody = null;
        InputStreamReader inputStreamReader;
        try (InputStream respBody = http.getInputStream()) {
            inputStreamReader = new InputStreamReader(respBody);
            //responseBody = new Gson().fromJson(inputStreamReader, responseType);
        }
        //return responseBody;
        return (T) inputStreamReader;
//        Object responseBody = "";
//        try (InputStream respBody = http.getInputStream()) {
//            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
//            responseBody = new Gson().fromJson(inputStreamReader, Map.class);
//        }
//        return (T)responseBody;
    }

    public void register(ArrayList<String> params) throws Exception {
        ArrayList<String> p = new ArrayList<>();
        p.add("POST");
        p.add("/user");
        p.add("{\"username\": \"" + params.get(0) + "\", \"password\": \"" + params.get(1) + "\", \"email\": \"" + params.get(2) + "\"}");
        main(p, RegisterResponse.class);
    }
}
