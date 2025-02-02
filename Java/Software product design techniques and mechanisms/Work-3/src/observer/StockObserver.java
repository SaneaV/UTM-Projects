package observer;

public class StockObserver implements Observer {

  private final String productName;

  public StockObserver(String productName) {
    this.productName = productName;
  }

  @Override
  public void update(String message) {
    System.out.println("\nУведомление для товара " + productName + ": " + message);
  }
}
