import org.example.CustomMath3;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CustomMathTest3 {

  private final int x;           // Numărătorul
  private final int y;           // Numitorul
  private final Object expected; // Rezultatul așteptat (valoare sau excepție)

  public CustomMathTest3(int x, int y, Object expected) {
    this.x = x;
    this.y = y;
    this.expected = expected;
  }

  // Furnizăm datele de intrare pentru testare
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][]{
        {10, 2, 5},         // Împărțire validă
        {9, 3, 3},          // Împărțire validă
        {10, 0, "Exception"} // Împărțire la 0, așteptăm excepția
    });
  }

  @Test
  public void testDivision() {
    try {
      int result = CustomMath3.division(x, y);
      if (expected instanceof String && expected.equals("Exception")) {
        fail("Excepția nu a fost generată pentru împărțirea la zero!");
      } else {
        assertEquals((int) expected, result);
      }
    } catch (IllegalArgumentException e) {
      if (!(expected instanceof String && expected.equals("Exception"))) {
        fail("Excepția nu trebuia generată pentru un numitor nenul!");
      }
    }
  }
}
