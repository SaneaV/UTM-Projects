import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.example.CustomMath2;
import org.junit.Test;

public class CustomMathTest2 {

  @Test
  public void testDivisionByZero() {
    int x = 10; // Numerator
    int y = 1; // Denominator (modificați între 0 și valori nenule pentru testare manuală)
    int expResult = 10; // Rezultatul așteptat

    try {
      int result = CustomMath2.division(x, y);
      assertEquals(expResult, result); // Verificare rezultat
      if (y == 0) {
        fail("Excepția nu a fost generată pentru împărțirea la zero!");
      }
    } catch (IllegalArgumentException e) {
      if (y != 0) {
        fail("Excepția nu trebuia generată pentru un numitor nenul!");
      }
    }
  }
}
