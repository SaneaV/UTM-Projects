package org.example;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.example.CustomMath4;
import org.junit.Test;

/**
 * Тестовый класс для проверки метода isPositive класса CustomMath4.
 */
public class CustomMathTest4 {

  /**
   * Тестирование метода isPositive.
   * Проверка того, является ли число положительным.
   */
  @Test
  public void testIsPositive() {
    // Проверка положительного числа
    assertTrue("Число должно быть положительным", CustomMath4.isPositive(10));
    // Проверка отрицательного числа
    assertFalse("Число не должно быть положительным", CustomMath4.isPositive(-5));
    // Проверка нуля
    assertFalse("Ноль не является положительным", CustomMath4.isPositive(0));
  }
}
