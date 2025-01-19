package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.example.CustomMath2;
import org.junit.Test;

/**
 * Тестовый класс для проверки методов класса CustomMath2.
 */
public class CustomMathTest2 {

  /**
   * Тест для проверки метода division на деление на ноль и корректность результата.
   */
  @Test
  public void testDivisionByZero() {
    int x = 10; // Числитель
    int y = 0;  // Знаменатель (меняйте между 0 и ненулевыми значениями для ручного тестирования)
    int expResult = 0; // Ожидаемый результат

    try {
      int result = CustomMath2.division(x, y);
      assertEquals(expResult, result); // Проверка результата
      if (y == 0) {
        fail("Исключение не было сгенерировано при делении на ноль!");
      }
    } catch (IllegalArgumentException e) {
      if (y != 0) {
        fail("Исключение не должно было быть сгенерировано для ненулевого знаменателя!");
      }
    }
  }
}
