class Order {

  private final Drink drink;
  private final String size;
  private final boolean sugar;

  private Order(Builder b) {
    this.drink = b.drink;
    this.size = b.size;
    this.sugar = b.sugar;
  }

  public String toString() {
    return drink.getName() + " | Size: " + size +
        " | Sugar: " + (sugar ? "Yes" : "No");
  }

  // Builder
  public static class Builder {

    private Drink drink;
    private String size;
    private boolean sugar;

    public Builder setDrink(Drink drink) {
      this.drink = drink;
      return this;
    }

    public Builder setSize(String size) {
      this.size = size;
      return this;
    }

    public Builder setSugar(boolean s) {
      this.sugar = s;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}