import responses.CreateGameResponse;
import responses.LoginResponse;
import responses.RegisterResponse;

import java.util.ArrayList;
import java.util.Objects;

public class ClientMain {
    private Boolean loggedIn = false;
    private String loggedInAuth = "";
    private final ClientServerFacade server = new ClientServerFacade();

    public static void main(String[] args) {
        var serverURL = "http://localhost:8080";
        if (args.length == 1) {
            serverURL = args[0];
        }
        new Repl(serverURL).run();
    }

    public String eval(String input) {
        String[] userInputs = input.split(" ");
        if (Objects.equals(userInputs[0], "help")) {
            return help();
        }
        else if (Objects.equals(userInputs[0], "quit")) {
            return quit();
        }
        else if (Objects.equals(userInputs[0], "login")) {
            return login(userInputs[1], userInputs[2]);
        }
        else if (Objects.equals(userInputs[0], "register")) {
            return register(userInputs[1], userInputs[2], userInputs[3]);
        }
        else if (Objects.equals(userInputs[0], "logout")) {
            return logout();
        }
        else if (Objects.equals(userInputs[0], "create")) {
            return create(userInputs[1]);
        }
        else if (Objects.equals(userInputs[0], "join")) {
            return join();
        }
        else if (Objects.equals(userInputs[0], "list")) {
            return list();
        }
        else if (Objects.equals(userInputs[0], "observe")) {
            return observe(userInputs[1]);
        }
        return null;
    }

    public String help() {
        if (!loggedIn) {
            return """
                register <USERNAME> <PASSWORD> <EMAIL> - to create an account
                login <USERNAME> <PASSWORD> - to play chess
                quit - stop the chess program
                help - with possible commands""";
        }
        else {
            //IF LOGGED IN:
            return """
                create <NAME> - a game
                list - games
                join <ID> [WHITE|BLACK|<empty>] - a game
                observe <ID> - a game
                logout - when you are done
                quit - stop the chess program
                help - with possible commands""";
        }
    }

    public String quit() {
        return null;
    }

    public String login(String username, String password) {
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);
        try {
            LoginResponse response = server.login(params);
            loggedIn = true;
            loggedInAuth = response.getAuthToken();
            return help();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String register(String username, String password, String email) {
        ArrayList<String> params = new ArrayList<>();
        params.add(username);
        params.add(password);
        params.add(email);
        try {
            RegisterResponse response = server.register(params);
            loggedIn = true;
            loggedInAuth = response.getAuth();
            return(help());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String logout() {
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            server.logout(loggedInAuth);
            loggedIn = false;
            return help();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String create(String name) {
        if (!loggedIn) {
            return "ERROR";
        }
        try {
            CreateGameResponse response = server.create(name, loggedInAuth);
            return "Game created!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String join() {
        if (!loggedIn) {
            return "ERROR";
        }
        return null;
    }
    public String list() {
        if (!loggedIn) {
            return "ERROR";
        }
        return null;
    }
    public String observe(String gameID) {
        int gID = Integer.parseInt(gameID);
        if (!loggedIn) {
            return "ERROR";
        }
        return null;
    }
}
