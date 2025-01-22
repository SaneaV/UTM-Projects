import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class ChatServer {

  private static final int PORT = 12345;
  public static Set<Socket> clientSockets = ConcurrentHashMap.newKeySet();
  public static List<String> messageHistory = Collections.synchronizedList(new ArrayList<>());
  private static int clientIdCounter = 0;

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server started on port " + PORT);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        clientSockets.add(clientSocket);
        int clientId = ++clientIdCounter;
        System.out.println("New client connected: " + clientSocket.getRemoteSocketAddress() + " with ID: " + clientId);

        sendHistoryToClient(clientSocket);
        new Thread(new ClientHandler(clientSocket, clientId)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void sendHistoryToClient(Socket clientSocket) {
    try {
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      synchronized (messageHistory) {
        for (String message : messageHistory) {
          out.println(message);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}