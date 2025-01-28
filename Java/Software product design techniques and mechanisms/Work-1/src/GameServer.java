import game.GameWorld;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameServer {
  private static final int PORT = 12345;
  private final ExecutorService clientPool = Executors.newCachedThreadPool();
  private final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();
  private final GameWorld world = GameWorld.getInstance();
  private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

  public static void main(String[] args) {
    new GameServer().startServer();
  }

  public void startServer() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Game server started on port " + PORT);

      executor.scheduleAtFixedRate(() -> {
        if (world.getCharacters().size() == 0 && world.getNumberOfPlayers() > 0) {
          System.out.println("Congratulations! Players won!");
          executor.shutdownNow();
        } else if (world.getCharacters().size() > 0 && world.getNumberOfPlayers() > 0){
          System.out.println("\n=== BATTLE ===");
          world.simulateCombat();
          System.out.println("\nRemaining characters:\n");
          world.getCharacters().forEach(character ->
              System.out.println("Name: " + character.getName() + ", Health: " + character.getHealth()));
        }
        else {
          System.out.println("The server is empty");
        }
      }, 1, 3, TimeUnit.SECONDS);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress());
        ClientHandler handler = new ClientHandler(clientSocket, this);
        clients.add(handler);
        clientPool.execute(handler);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      clientPool.shutdown();
    }
  }

  public void removeClient(ClientHandler client) {
    clients.remove(client);
  }
}
