import java.util.ArrayList;
import java.util.Objects;

public class ClientMain {
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

        }
        else if (Objects.equals(userInputs[0], "login")) {

        }
        else if (Objects.equals(userInputs[0], "register")) {

        }
        else if (Objects.equals(userInputs[0], "logout")) {

        }
        else if (Objects.equals(userInputs[0], "create")) {

        }
        else if (Objects.equals(userInputs[0], "join")) {

        }
        else if (Objects.equals(userInputs[0], "list")) {

        }
        else if (Objects.equals(userInputs[0], "observe")) {

        }
        return null; //DELETE
    }

    public String help() {
        //IF LOGGED OUT:
        if (true) {
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

    public String login() {
        return null;
    }

    public String register() {
        return null;
    }

    public String logout() {
        return null;
    }

    public String create() {
        return null;
    }

    public String join() {
        return null;
    }
    public String list() {
        return null;
    }
    public String observe() {
        return null;
    }
}
