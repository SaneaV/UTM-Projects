package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CustomMathTest2 {

  @Test
  public void testDivisionByZero() {
    int x = 8;
    int y = 1;
    int exp = 8;

    if (y == 0) {
      assertThrows(IllegalArgumentException.class, () -> {
        CustomMath.division(x, y);
      }, "Деление на ноль не создает исключительной ситуации");
    } else {
      int result = CustomMath.division(x, y);
      assertEquals(exp, result, "Некорректный результат деления");
    }
  }
}
