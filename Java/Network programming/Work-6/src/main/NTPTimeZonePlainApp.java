import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

public class NTPTimeZonePlainApp {

  private static final String NTP_SERVER = "pool.ntp.org";
  private static final int NTP_PORT = 123;
  private static final int NTP_PACKET_SIZE = 48;
  private static final long NTP_TIMESTAMP_OFFSET = 2208988800L;

  public static Date getExactTimeFromNTP() throws IOException {
    InetAddress serverAddress = InetAddress.getByName(NTP_SERVER);
    DatagramSocket socket = new DatagramSocket();
    socket.setSoTimeout(5000);

    byte[] buffer = new byte[NTP_PACKET_SIZE];
    buffer[0] = 0b00100011;

    DatagramPacket request = new DatagramPacket(buffer, buffer.length, serverAddress, NTP_PORT);
    socket.send(request);

    DatagramPacket response = new DatagramPacket(buffer, buffer.length);
    socket.receive(response);
    socket.close();

    long secondsSince1900 = ((buffer[40] & 0xFFL) << 24) |
        ((buffer[41] & 0xFFL) << 16) |
        ((buffer[42] & 0xFFL) << 8) |
        (buffer[43] & 0xFFL);

    long unixTime = (secondsSince1900 - NTP_TIMESTAMP_OFFSET) * 1000;
    return new Date(unixTime);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Приложение: Получение точного времени через NTP");

    while (true) {
      System.out.print("\nВведите временную зону в формате 'GMT+X', 'GMT-X' или просто 'GMT'. Напишите 'exit' для выхода: ");
      String input = scanner.nextLine().trim();

      if (input.equalsIgnoreCase("exit")) {
        System.out.println("Выход из приложения. До свидания!");
        break;
      }

      if (!input.matches("^GMT([+-]\\d{1,2})?$")) {
        System.out.println("Неверный формат. Пример правильного ввода: GMT, GMT+2, GMT-5");
        continue;
      }

      try {
        String zoneId = input.equals("GMT") ? "GMT+0" : input;

        int offset = Integer.parseInt(zoneId.substring(4));
        if (offset < -11 || offset > 11) {
          System.out.println("Ошибка: допустимый диапазон от GMT-11 до GMT+11.");
          continue;
        }

        Date ntpTime = getExactTimeFromNTP();

        TimeZone userTimeZone = TimeZone.getTimeZone(zoneId);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(userTimeZone);

        String formattedTime = formatter.format(ntpTime);
        System.out.println("Точное время в зоне " + zoneId + ": " + formattedTime);
      } catch (Exception e) {
        System.out.println("Ошибка при получении времени: " + e.getMessage());
      }
    }

    scanner.close();
  }
}
