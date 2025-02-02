package chain;

import model.Request;

public class StockCheckHandler extends AbstractHandler {

  @Override
  public void handleRequest(Request request) {
    final String name = request.getProduct().getName();
    System.out.println("\nПОЯВИЛСЯ ЗАКАЗ НА ТОВАР: " + name);

    if (request.getQuantity() <= request.getProduct().getStock()) {
      System.out.println("Товар " + name + " доступен в наличии. В количестве - " +
          request.getProduct().getStock() + " единиц(ы)");
      super.handleRequest(request);
    } else {
      System.out.println("Ошибка: Недостаточно товара " + name + " на складе.");
    }
  }
}
