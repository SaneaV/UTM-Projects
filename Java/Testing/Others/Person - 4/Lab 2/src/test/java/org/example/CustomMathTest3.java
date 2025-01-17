package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CustomMathTest3 {

  @ParameterizedTest
  @CsvSource({
      "8, 2, 4",
      "8, -2, -4",
      "8, 0, 0"
  })
  public void testDivisionByZero(int x, int y, int expResult) {
    if (y == 0) {
      assertThrows(IllegalArgumentException.class, () -> {
        CustomMath.division(x, y);
      }, "Деление на ноль не создает исключительной ситуации");
    } else {
      int result = CustomMath.division(x, y);
      assertEquals(expResult, result, "Некорректный результат деления");
    }
  }

}
