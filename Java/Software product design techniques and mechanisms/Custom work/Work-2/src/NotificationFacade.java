class NotificationFacade {

  private Notification email;
  private Notification sms;

  public NotificationFacade(String phone) {
    email = new EmailNotification();
    sms = new SmsAdapter(new SmsService(), phone);
  }

  // Простое API для отправки обоих уведомлений
  public void sendAll(String message) {
    email.send(message);
    sms.send(message);
  }
}