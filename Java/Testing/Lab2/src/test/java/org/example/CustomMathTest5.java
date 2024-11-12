package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomMathTest5 {

  @Test
  public void testSum() {
    int x = 2;
    int y = 3;
    int expResult = 5;
    int result = CustomMath.sum(x, y);
    assertEquals(expResult, result, "Sum method failed");
  }

  @Test
  public void testDivisionByZero() {
    int x = 6;
    int y = 0;

    // Проверка на деление на ноль
    assertThrows(IllegalArgumentException.class, () -> CustomMath.division(x, y), "Expected exception for division by zero");
  }

  @Test
  public void testDivisionWithNonZeroDenominator() {
    int x = 6;
    int y = 2;
    int expResult = 3;
    int result = CustomMath.division(x, y);
    assertEquals(expResult, result, "Division method failed");
  }

  @Test
  public void testIsEven() {
    // Проверка, что четное число возвращает true
    assertTrue(CustomMath5.isEven(4), "Expected true for even number");

    // Проверка, что нечетное число возвращает false
    assertFalse(CustomMath5.isEven(3), "Expected false for odd number");
  }
}
