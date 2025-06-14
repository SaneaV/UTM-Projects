class PhoneDisplay implements Observer {

  public void update(int temperature) {
    System.out.println("PhoneDisplay: Temp is " + temperature);
  }
}