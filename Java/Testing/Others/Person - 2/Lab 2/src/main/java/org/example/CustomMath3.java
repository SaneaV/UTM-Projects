  package org.example;

  /**
   * Класс CustomMath3 предоставляет основные математические операции,
   * такие как сложение и деление.
   */
  public class CustomMath3 {

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
     * Главный метод. На текущий момент не содержит логики.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
      // Здесь можно добавить тестовый вызов методов
    }
  }
