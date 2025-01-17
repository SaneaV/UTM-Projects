package org.example;

public class CustomMath5 {

  public static int sum(int x, int y) {
    return x + y;
  }

  public static int division(int x, int y) {
    if (y == 0) {
      throw new IllegalArgumentException("divider is 0");
    }
    return x / y;
  }

  // Новый метод для проверки, является ли число четным
  public static boolean isEven(int number) {
    return number % 2 == 0;
  }

  public static void main(String[] args) {
  }
}

