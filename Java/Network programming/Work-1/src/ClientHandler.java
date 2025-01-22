import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private final Socket socket;
  private final int clientId;

  public ClientHandler(Socket socket, int clientId) {
    this.socket = socket;
    this.clientId = clientId;
  }

  @Override
  public void run() {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

      String message;
      while ((message = in.readLine()) != null) {
        String formattedMessage = "Client " + clientId + ": " + message;
        System.out.println(formattedMessage);
        addMessageToHistory(formattedMessage);
        broadcastMessage(formattedMessage);
      }

    } catch (IOException e) {
      System.out.println("Client disconnected: " + socket.getRemoteSocketAddress());
    } finally {
      ChatServer.clientSockets.remove(socket);
      try {
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void addMessageToHistory(String message) {
    synchronized (ChatServer.messageHistory) {
      ChatServer.messageHistory.add(message);
    }
  }

  private void broadcastMessage(String message) {
    for (Socket clientSocket : ChatServer.clientSockets) {
      if (!clientSocket.isClosed() && !clientSocket.equals(this.socket)) {
        try {
          PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
          out.println(message);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}