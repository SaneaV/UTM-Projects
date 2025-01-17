package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CustomMathTest3 {

  @ParameterizedTest
  @CsvSource({
      "6, 1, 6",   // Обычное деление: 6 / 1 = 6
      "6, -1, -6", // Обычное деление: 6 / -1 = -6
      "6, 0, 0"    // Деление на ноль
  })
  public void testDivisionByZero(int x, int y, int expResult) {
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
