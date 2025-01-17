package org.example;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Тестовый класс для проверки методов класса CustomMath.
 */
public class CustomMathTest1 {

  /**
   * Метод, выполняемый перед запуском всех тестов.
   */
  @BeforeClass
  public static void setUpClass() {
    System.out.println("Начало тестирования класса CustomMath.");
  }

  /**
   * Метод, выполняемый после завершения всех тестов.
   */
  @AfterClass
  public static void tearDownClass() {
    System.out.println("Завершение тестирования класса CustomMath.");
  }

  /**
   * Тест для проверки метода sum.
   */
  @Test
  public void testSum() {
    System.out.println("Тестирование метода sum.");
    int x = 2; // Первое число
    int y = 3; // Второе число
    int expResult = 5; // Ожидаемый результат
    int result = CustomMath.sum(x, y); // Фактический результат
    assertEquals("Метод sum работает некорректно", expResult, result);
  }
}
