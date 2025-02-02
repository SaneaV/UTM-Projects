package model;

public class Request {

  private final Product product;
  private final int quantity;

  public Request(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }
}
