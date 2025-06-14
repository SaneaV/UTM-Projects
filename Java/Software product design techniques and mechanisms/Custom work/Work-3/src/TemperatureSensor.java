import java.util.ArrayList;
import java.util.List;

class TemperatureSensor implements Subject {

  private int temperature;
  private List<Observer> observers = new ArrayList<>();

  public void setTemperature(int temp) {
    this.temperature = temp;
    notifyObservers();
  }

  public void addObserver(Observer o) {
    observers.add(o);
  }

  public void removeObserver(Observer o) {
    observers.remove(o);
  }

  public void notifyObservers() {
    for (Observer o : observers) {
      o.update(temperature);
    }
  }
}