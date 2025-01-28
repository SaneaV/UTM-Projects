import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 12345;

  public static void main(String[] args) {
    try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

      System.out.println("Welcome to the game!");
      System.out.println("Type 'exit' to disconnect.");

      String serverMessage;
      while ((serverMessage = in.readLine()) != null) {
        System.out.println(serverMessage);

        if (serverMessage.contains("Enter your name:")) {
          String playerName = consoleInput.readLine();
          out.println(playerName);
        } else {
          String command = consoleInput.readLine();
          System.out.println("You disconnected");
          out.println(command);
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
