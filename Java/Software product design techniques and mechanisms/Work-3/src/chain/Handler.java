package chain;

import model.Request;

public interface Handler {

  void setNextHandler(Handler nextHandler);

  void handleRequest(Request request);
}
