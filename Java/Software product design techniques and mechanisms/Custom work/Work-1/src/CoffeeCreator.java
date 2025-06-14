class CoffeeCreator extends DrinkCreator {

  public Drink createDrink() {
    return new Coffee();
  }
}