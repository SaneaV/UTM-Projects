package org.example;

import static org.testng.AssertJUnit.assertEquals;

import org.junit.jupiter.api.Test;

public class CustomMathTest1 {

  @Test
  public void testSum() {
    System.out.println("sum");
    int x = 5;
    int y = 4;
    int expResult = 9;
    int result = CustomMath.sum(x, y);
    assertEquals("Sum method failed", expResult, result);
  }
}

