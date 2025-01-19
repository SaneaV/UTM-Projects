package org.example;

/**
 * Класс CustomMath4 предоставляет основные математические операции
 * и метод для проверки, является ли число положительным.
 */
public class CustomMath4 {

  /**
   * Метод для сложения двух чисел.
   *
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
   *
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
   * Метод для проверки, является ли число положительным.
   *
   * @param x число для проверки
   * @return true, если x > 0, иначе false
   */
  public static boolean isPositive(int x) {
    return x > 0;
  }

  /**
   * Главный метод. На данный момент не содержит логики.
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) {
    // Здесь можно добавить тестовый вызов методов
  }
}
