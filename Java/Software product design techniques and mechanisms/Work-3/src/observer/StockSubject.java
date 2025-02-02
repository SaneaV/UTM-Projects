package observer;

import java.util.ArrayList;
import java.util.List;
import model.Product;

public class StockSubject {

  private final List<Observer> observers = new ArrayList<>();
  private final Product product;

  public StockSubject(Product product) {
    this.product = product;
  }

  public void addObserver(Observer observer) {
    observers.add(observer);
  }

  public void notifyObservers(String message) {
    for (Observer observer : observers) {
      observer.update(message);
    }
  }

  public void checkStock() {
    if (product.getStock() < 5) {
      notifyObservers("Критически низкий уровень запасов!");
    }
    if (product.getStock() > 50)
      notifyObservers("Высокий уровень запасов!");
  }
}
