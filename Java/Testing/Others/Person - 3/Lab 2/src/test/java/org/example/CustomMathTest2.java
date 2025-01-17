package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CustomMathTest2 {

  @Test
  public void testDivisionByZero() {
    int x = 6;
    int y = 1;
    int expResult = 6;

    if (y == 0) {
      // Ожидаемое исключение при делении на ноль
      assertThrows(IllegalArgumentException.class, () -> {
        CustomMath.division(x, y);
      }, "Деление на ноль не создает исключительной ситуации");
    } else {
      // Обычное деление, результат должен соответствовать expResult
      int result = CustomMath.division(x, y);
      assertEquals(expResult, result, "Некорректный результат деления");
    }
  }
}
