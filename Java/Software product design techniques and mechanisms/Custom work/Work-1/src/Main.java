public class Main {

  public static void main(String[] args) {
    // Singleton
    Settings settings = Settings.getInstance();
    settings.setLanguage("English");

    // Factory Method: выбираем конкретного «создателя»
    DrinkCreator coffeeCreator = new CoffeeCreator();
    DrinkCreator teaCreator = new TeaCreator();

    Drink coffee = coffeeCreator.createDrink(); // продукт создаёт подкласс
    Drink tea = teaCreator.createDrink();

    System.out.println("Created drink: " + coffee.getName());
    System.out.println("Created drink: " + tea.getName());

    // Builder
    Order order = new Order.Builder()
        .setDrink(coffee)
        .setSize("Large")
        .setSugar(true)
        .build();

    System.out.println("Order created: " + order);

    // Проверка Singleton
    System.out.println("Singleton check: " +
        (settings == Settings.getInstance())); // true
  }
}