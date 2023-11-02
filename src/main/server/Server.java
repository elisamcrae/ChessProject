package server;
import dataAccess.DataAccessException;
import dataAccess.Database;
import handlers.*;
import spark.Spark;

import java.sql.SQLException;

public class Server {
    //ADDING:
    private static Database db = new Database();

    public static void main(String[] args) throws SQLException, DataAccessException {
        configureDatabase();
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

    //ADDING
    static void configureDatabase() throws SQLException, DataAccessException {
        try (var conn = db.getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();

            conn.setCatalog("chess");

            //CREATE AUTH
            var createAuthTable = """
            CREATE TABLE IF NOT EXISTS auth (
                userID INT NOT NULL,
                token VARCHAR(255) NOT NULL,
                PRIMARY KEY (userID)
            )""";

            try (var createTableStatement = conn.prepareStatement(createAuthTable)) {
                createTableStatement.executeUpdate();
            }

            //CREATE GAME
            var createGameTable = """
            CREATE TABLE IF NOT EXISTS game (
                gameID INT NOT NULL AUTO_INCREMENT,
                whitePlayer VARCHAR(255),
                blackPlayer VARCHAR(255),
                gameName VARCHAR(255) NOT NULL,
                games longtext NOT NULL,
                observers longtext,
                PRIMARY KEY (gameID)
            )""";

            try (var createTableStatement = conn.prepareStatement(createGameTable)) {
                createTableStatement.executeUpdate();
            }

            //CREATE USER
            var createUserTable = """
            CREATE TABLE IF NOT EXISTS user (
                userID INT NOT NULL AUTO_INCREMENT,
                username VARCHAR(255) NOT NULL,
                password VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (userID)
            )""";

            try (var createTableStatement = conn.prepareStatement(createUserTable)) {
                createTableStatement.executeUpdate();
            }
        }
    }
}
