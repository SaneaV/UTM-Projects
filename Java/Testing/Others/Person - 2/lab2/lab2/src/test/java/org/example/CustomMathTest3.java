package org.example;

import org.example.CustomMath3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * Параметризованный тест для проверки метода division класса CustomMath3.
 */
@RunWith(Parameterized.class)
public class CustomMathTest3 {

  private final int x;           // Числитель
  private final int y;           // Знаменатель
  private final Object expected; // Ожидаемый результат (значение или исключение)

  /**
   * Конструктор для передачи параметров в тест.
   *
   * @param x        Числитель
   * @param y        Знаменатель
   * @param expected Ожидаемый результат
   */
  public CustomMathTest3(int x, int y, Object expected) {
    this.x = x;
    this.y = y;
    this.expected = expected;
  }

  /**
   * Данные для параметризованного теста.
   *
   * @return Список наборов данных {числитель, знаменатель, ожидаемый результат}.
   */
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {10, 2, 5},          // Корректное деление
        {9, 3, 3},           // Корректное деление
        {10, 0, "Exception"} // Деление на 0, ожидается исключение
    });
  }

  /**
   * Тестирование метода division.
   */
  @Test
  public void testDivision() {
    try {
      int result = CustomMath3.division(x, y);
      if (expected instanceof String && expected.equals("Exception")) {
        fail("Исключение не было сгенерировано при делении на ноль!");
      } else {
        assertEquals((int) expected, result);
      }
    } catch (IllegalArgumentException e) {
      if (!(expected instanceof String && expected.equals("Exception"))) {
        fail("Исключение не должно быть сгенерировано для ненулевого знаменателя!");
      }
    }
  }
}
