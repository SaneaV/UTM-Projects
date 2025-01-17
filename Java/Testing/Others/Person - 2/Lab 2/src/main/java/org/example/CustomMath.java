package org.example;

/**
 * Класс CustomMath предоставляет основные математические операции,
 * такие как сложение и деление.
 */
public class CustomMath {

  /**
   * Метод для сложения двух чисел.
   * @param x первое число
   * @param y второе число
   * @return сумма чисел x и y
   */
  public static int sum(int x, int y) {
    return x + y;
  }

  /**
   * Метод для деления двух чисел.
   * Выбрасывает исключение, если делитель равен нулю.
   * @param x делимое
   * @param y делитель
   * @return результат деления x на y
   * @throws IllegalArgumentException если делитель равен 0
   */
  public static int division(int x, int y) {
    if (y == 0) {
      throw new IllegalArgumentException("Делитель равен 0");
    }
    return x / y;
  }

  /**
   * Главный метод для тестирования функциональности деления.
   * Проверяет корректность обработки деления на 0.
   * @param args аргументы командной строки (не используются)
   */
  public static void main(String[] args) {
    try {
      // Пробуем выполнить деление 1 на 0
      int z = division(1, 0);
      // Если исключение не выброшено, тест не прошел
      System.out.println("Тест3 провален.");
    } catch (IllegalArgumentException e) {
      // Если исключение выброшено, тест успешен
      System.out.println("Тест3 пройден.");
    }
  }
}
