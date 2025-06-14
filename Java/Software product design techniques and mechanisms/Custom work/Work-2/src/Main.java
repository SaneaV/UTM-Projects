public class Main {

  public static void main(String[] args) {
    String phone = "+1234567890";

    // Создаем фасад для отправки уведомлений
    NotificationFacade facade = new NotificationFacade(phone);

    // Отправляем с логированием (декоратор)
    Notification loggedEmail = new LoggingDecorator(new EmailNotification());
    loggedEmail.send("Hello via Email with logging!");

    System.out.println();

    // Используем фасад, который отправит Email и SMS
    facade.sendAll("Hello to all channels!");

    System.out.println();

    // Отправляем SMS через адаптер с логированием
    Notification sms = new LoggingDecorator(new SmsAdapter(new SmsService(), phone));
    sms.send("Hello SMS with logging!");
  }
}