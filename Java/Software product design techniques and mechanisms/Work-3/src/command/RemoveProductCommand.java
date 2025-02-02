package command;

import model.Product;

public class RemoveProductCommand implements Command {
  private Product product;
  private int quantity;
  private int previousStock; // Храним предыдущее состояние

  public RemoveProductCommand(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  @Override
  public void execute() {
    if (product.getStock() >= quantity) {
      previousStock = product.getStock();
      product.setStock(product.getStock() - quantity);
      System.out.println("\nСписано " + quantity + " единиц товара " + product.getName() + ".");
    } else {
      System.out.println("\nОшибка: Недостаточно товара " + product.getName() + " для списания.");
    }
  }

  @Override
  public void undo() {
    product.setStock(previousStock);
    System.out.println("\nОтменено списание " + quantity + " единиц товара " + product.getName() + ".");
  }
}
