package org.example;

public class CustomMath4 {

  public static int sum(int x, int y) {
    return x + y;
  }

  public static int division(int x, int y) {
    if (y == 0) {
      throw new IllegalArgumentException("divider is 0");
    }
    return x / y;
  }

  // Noua metodă: verifică dacă un număr este pozitiv
  public static boolean isPositive(int x) {
    return x > 0;
  }

  public static void main(String[] args) {
  }
}
