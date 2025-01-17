package org.example;

public class CustomMath1 {

  public static int sum(int x, int y) {
    return x + y;
  }

  public static int division(int x, int y) {
    if (y == 0) {
      throw new IllegalArgumentException("divider is 0");
    }
    return x / y;
  }

  public static void main(String[] args) {
    try {
      int z = division(1, 0);
      System.out.println("Test3 failed.");
    } catch (IllegalArgumentException e) {
      // Testul se considera reusit daca incercarea de impartire
      // la 0 genereaza exceptia asteptata
      System.out.println("Test3 passed.");
    }
  }
}
