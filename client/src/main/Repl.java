import java.util.Scanner;

public class Repl {
    private ClientMain myClient;

    public Repl(String serverURL) {
        myClient = new ClientMain();
    }

    public void run() {
        //Print out the welcome message
        System.out.println("Welcome to 240 Chess. Type 'help' to get started.");

        Scanner scanner = new Scanner(System.in);
        String result = "";
        while (result != "quit") {
            String next = scanner.nextLine();
            System.out.println(myClient.eval(next));
        }
    }
}
