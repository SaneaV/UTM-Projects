import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 12345;

  public static void main(String[] args) {
    try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

      System.out.println("Connected to the chat server");

      new Thread(() -> {
        String serverMessage;
        try {
          while ((serverMessage = in.readLine()) != null) {
            System.out.println(serverMessage);
          }
        } catch (IOException e) {
          System.out.println("Disconnected from server.");
        }
      }).start();

      String userMessage;
      while ((userMessage = consoleInput.readLine()) != null) {
        out.println(userMessage);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
