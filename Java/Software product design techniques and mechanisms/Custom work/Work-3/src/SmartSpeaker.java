class SmartSpeaker implements Observer {

  public void update(int temperature) {
    System.out.println("SmartSpeaker: Temp changed to " + temperature);
  }
}