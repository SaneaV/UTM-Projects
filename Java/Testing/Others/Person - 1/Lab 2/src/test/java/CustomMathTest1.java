import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.CustomMath1;
import org.junit.Test;

public class CustomMathTest1 {

  /**
   * Test pentru metoda sum din clasa CustomMath.
   */
  @Test
  public void testSum() {
    System.out.println("Testarea funcției sum");
    int x = 1; // Valoarea de test 1
    int y = 3; // Valoarea de test 2
    int expResult = 4; // Rezultatul așteptat
    int result = CustomMath1.sum(x, y);
    assertEquals(expResult, result); // Verificarea rezultatului
  }
}

