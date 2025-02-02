package command;

import model.Product;

public class AddProductCommand implements Command {
  private final Product product;
  private final int quantity;
  private int previousStock;

  public AddProductCommand(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  @Override
  public void execute() {
    previousStock = product.getStock();
    product.setStock(product.getStock() + quantity);
    System.out.println("\nДобавлено " + quantity + " единиц товара " + product.getName() + ".");
  }

  @Override
  public void undo() {
    product.setStock(previousStock);
    System.out.println("\nОтменено добавление " + quantity + " единиц товара " + product.getName() + ".");
  }
}