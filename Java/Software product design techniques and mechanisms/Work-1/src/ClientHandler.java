import game.GameWorld;
import game.Player;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private final Socket clientSocket;
  private final GameServer server;

  public ClientHandler(Socket clientSocket, GameServer server) {
    this.clientSocket = clientSocket;
    this.server = server;
  }

  @Override
  public void run() {
    try (
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
    ) {
      out.println("Enter your name:");
      String playerName = in.readLine();

      if (playerName != null && !playerName.isBlank()) {
        Player newPlayer = new Player(playerName);
        GameWorld.getInstance().addPlayer(newPlayer);
        out.println("game.Player \"" + playerName + "\" joined the game!");

        while (true) {
          String command = in.readLine();
          if ("exit".equalsIgnoreCase(command)) {
            GameWorld.getInstance().disconnectPlayer(playerName);
            System.out.println("game.Player " + playerName + " was disconnected.");
            break;
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        server.removeClient(this);
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
