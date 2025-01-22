import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Scanner;

public class UDPChatClient {

  private static final int MULTICAST_PORT = 9876;
  private static final String MULTICAST_GROUP = "230.0.0.1";

  public static void main(String[] args) {
    try (MulticastSocket multicastSocket = new MulticastSocket(MULTICAST_PORT);
        DatagramSocket socket = new DatagramSocket();
        Scanner scanner = new Scanner(System.in)) {

      // Get the address of the multicast group
      InetAddress group = InetAddress.getByName(MULTICAST_GROUP);
      // Get the network interface associated with the group address
      NetworkInterface netIf = NetworkInterface.getByInetAddress(group);
      // Join the multicast group on the specified network interface
      multicastSocket.joinGroup(new InetSocketAddress(group, MULTICAST_PORT), netIf);

      System.out.println("Client listening on port: " + socket.getLocalPort());

      // Thread to receive multicast messages
      Thread receiverThread = new Thread(() -> receiveMessages(multicastSocket, "Multicast message"));
      // Thread to receive private messages
      Thread privateReceiverThread = new Thread(() -> receiveMessages(socket, "Private message"));

      receiverThread.start();
      privateReceiverThread.start();

      System.out.println(
          "Enter messages to send (type 'exit' to quit, or '/private <address:port> <message>' for private messages):");
      while (true) {
        String message = scanner.nextLine();

        if (message.equalsIgnoreCase("exit")) {
          System.out.println("Exiting chat...");
          multicastSocket.leaveGroup(new InetSocketAddress(group, MULTICAST_PORT), netIf);
          multicastSocket.close();
          socket.close();
          receiverThread.interrupt();
          privateReceiverThread.interrupt();
          break;
        }

        if (message.startsWith("/private")) {
          sendPrivateMessage(socket, message);
        } else {
          sendMulticastMessage(socket, group, message);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Method to receive messages (both multicast and private)
  private static void receiveMessages(DatagramSocket socket, String messageType) {
    byte[] buffer = new byte[1024];
    while (!Thread.currentThread().isInterrupted()) {
      try {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        String message = new String(packet.getData(), 0, packet.getLength());
        System.out.printf("%s received from %s:%d: %s%n",
            messageType, packet.getAddress().getHostAddress(), packet.getPort(), message);
      } catch (SocketException e) {
        if (socket.isClosed()) {
          break;
        }
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
        break;
      }
    }
  }

  // Method to send private messages
  private static void sendPrivateMessage(DatagramSocket socket, String message) {
    String[] parts = message.split(" ", 3);
    if (parts.length == 3) {
      String target = parts[1];
      String privateMessage = parts[2];

      String[] addressParts = target.split(":");
      if (addressParts.length == 2) {
        try {
          String targetAddress = addressParts[0];
          int targetPort = Integer.parseInt(addressParts[1]);

          byte[] sendData = privateMessage.getBytes();
          DatagramPacket privatePacket = new DatagramPacket(
              sendData, sendData.length, InetAddress.getByName(targetAddress), targetPort);
          socket.send(privatePacket);
          System.out.println("Private message sent to " + target);
        } catch (Exception e) {
          System.out.println("Failed to send private message: " + e.getMessage());
        }
      } else {
        System.out.println("Invalid target format. Use address:port.");
      }
    } else {
      System.out.println("Invalid private message format. Use '/private <address:port> <message>'");
    }
  }

  // Method to send multicast messages
  private static void sendMulticastMessage(DatagramSocket socket, InetAddress group, String message) {
    try {
      byte[] sendData = message.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, MULTICAST_PORT);
      socket.send(sendPacket);
    } catch (Exception e) {
      System.out.println("Failed to send multicast message: " + e.getMessage());
    }
  }
}