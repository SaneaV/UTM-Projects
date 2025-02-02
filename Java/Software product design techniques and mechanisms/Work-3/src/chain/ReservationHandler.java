package chain;

import model.Request;

public class ReservationHandler extends AbstractHandler {

  @Override
  public void handleRequest(Request request) {
    System.out.println("Товар " + request.getProduct().getName() + " зарезервирован. В количестве " + request.getQuantity() +
        " единиц(ы)");
    request.getProduct().setStock(request.getProduct().getStock() - request.getQuantity());
    super.handleRequest(request);
  }
}
