import java.util.Scanner;

public class Repl {
    private ClientServerFacade myClientFacade;

    public Repl(String serverURL) {
        myClientFacade = new ClientServerFacade();
    }

    public void run() {
        //Print out the welcome message
        System.out.println("Welcome to 240 Chess. Type 'help' to get started.");

        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (result != "quit") {
            String next = scanner.next();

            //FIXME
        }
    }
}
