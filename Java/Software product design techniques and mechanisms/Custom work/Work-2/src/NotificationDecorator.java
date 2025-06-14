class NotificationDecorator implements Notification {

  protected Notification wrappee;

  public NotificationDecorator(Notification wrappee) {
    this.wrappee = wrappee;
  }

  public void send(String message) {
    wrappee.send(message);
  }
}