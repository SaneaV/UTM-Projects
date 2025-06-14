public class Main {

  public static void main(String[] args) {
    // Наблюдатель
    TemperatureSensor sensor = new TemperatureSensor();
    sensor.addObserver(new PhoneDisplay());
    sensor.addObserver(new SmartSpeaker());

    sensor.setTemperature(25);
    sensor.setTemperature(30);

    // Стратегия
    CleaningRobot robot = new CleaningRobot();
    robot.setStrategy(new VacuumCleaning());
    robot.clean(); // Пылесосим

    robot.setStrategy(new WetCleaning());
    robot.clean(); // Моём

    // Команда
    Light light = new Light();
    Command turnOn = new TurnOnCommand(light);
    Command turnOff = new TurnOffCommand(light);
    RemoteControl remote = new RemoteControl();

    remote.setCommand(turnOn);
    remote.pressButton();

    remote.setCommand(turnOff);
    remote.pressButton();
  }
}