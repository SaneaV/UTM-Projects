class LoggingDecorator extends NotificationDecorator {

  public LoggingDecorator(Notification wrappee) {
    super(wrappee);
  }

  public void send(String message) {
    System.out.println("[LOG] Sending notification...");
    super.send(message);
    System.out.println("[LOG] Notification sent.");
  }
}