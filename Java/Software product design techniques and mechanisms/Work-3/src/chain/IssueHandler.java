package chain;

import model.Request;

public class IssueHandler extends AbstractHandler {

  @Override
  public void handleRequest(Request request) {
    System.out.println("Товар " + request.getProduct().getName() + " выдан. В количестве " + request.getQuantity() +
        " единиц(ы)");
  }
}
