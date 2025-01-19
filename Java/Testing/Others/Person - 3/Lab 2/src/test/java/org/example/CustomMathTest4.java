package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CustomMathTest4 {

  @Test
  public void testSum() {
    int x = 2;
    int y = 3;
    int expResult = 5;
    int result = CustomMath4.sum(x, y);
    assertEquals(expResult, result, "Sum method failed");
  }

  @Test
  public void testDivisionByZero() {
    int x = 6;
    int y = 0;

    // Проверка на деление на ноль
    assertThrows(IllegalArgumentException.class, () -> CustomMath4.division(x, y), "Expected exception for division by zero");
  }

  @Test
  public void testDivisionWithNonZeroDenominator() {
    int x = 6;
    int y = 2;
    int expResult = 3;
    int result = CustomMath4.division(x, y);
    assertEquals(expResult, result, "Division method failed");
  }

  @Test
  public void testIsPositive() {
    // Проверка, что положительное число возвращает true
    assertTrue(CustomMath4.isPositive(5), "Expected true for positive number");

    // Проверка, что ноль возвращает false
    assertFalse(CustomMath4.isPositive(0), "Expected false for zero");

    // Проверка, что отрицательное число возвращает false
    assertFalse(CustomMath4.isPositive(-3), "Expected false for negative number");
  }
}

