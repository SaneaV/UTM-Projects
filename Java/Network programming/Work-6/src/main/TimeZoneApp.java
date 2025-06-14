import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TimeZoneApp {

  public static String getTimeInTimeZone(String timeZoneStr) {
    try {
      // Extract the offset value from the input string
      int offset = Integer.parseInt(timeZoneStr.substring(4));
      // Adjust the sign based on GMT+X or GMT-X
      ZoneOffset zoneOffset = timeZoneStr.contains("+") ? ZoneOffset.ofHours(offset) : ZoneOffset.ofHours(-offset);
      // Get the current time in the specified time zone
      ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneOffset.normalized());
      // Format the time as a string
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return zonedDateTime.format(formatter);
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.println("Enter the time zone in the format 'GMT+X' or 'GMT-X' (example: GMT+2 or GMT-5). Type 'exit' to quit: ");
      String userInput = scanner.nextLine().trim();

      if (userInput.equalsIgnoreCase("exit")) {
        break;
      }

      if (!userInput.startsWith("GMT+") && !userInput.startsWith("GMT-")) {
        System.out.println("Incorrect format. Please enter the time zone in the correct format.");
        continue;
      }

      String timeInTimeZone = getTimeInTimeZone(userInput);
      System.out.println("The exact time in " + userInput + " is: " + timeInTimeZone);
    }

    System.out.println("Exiting the application. Goodbye!");
    scanner.close();
  }
}