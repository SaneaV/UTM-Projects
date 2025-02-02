package chain;

import model.Request;

public abstract class AbstractHandler implements Handler {

  protected Handler nextHandler;

  @Override
  public void setNextHandler(Handler nextHandler) {
    this.nextHandler = nextHandler;
  }

  @Override
  public void handleRequest(Request request) {
    if (nextHandler != null) {
      nextHandler.handleRequest(request);
    }
  }
}
