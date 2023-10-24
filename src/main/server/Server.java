package server;
import handlers.*;
import model.AuthToken;
import requests.CreateGameRequest;
import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import services.*;
import spark.Request;
import spark.Response;
import spark.Spark;

import static spark.Spark.delete;
import static spark.Spark.post;

public class Server {
    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a directory for hosting static files
        Spark.externalStaticFileLocation("web");

        // Register handlers for each endpoint using the method reference syntax
        Spark.post("/session", new LoginHandler());
        Spark.post("/user", new RegisterHandler());
        Spark.post("/game", new CreateGameHandler());
        Spark.delete("/db", new ClearHandler());
        Spark.delete("/session", new LogoutHandler());
        Spark.get("/game", new ListGamesHandler());
        Spark.put("/game", new JoinGameHandler());
    }
}
