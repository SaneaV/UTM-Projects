import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.example.CustomMath4;
import org.junit.Test;

public class CustomMathTest4 {

  @Test
  public void testIsPositive() {
    assertTrue("Numărul ar trebui să fie pozitiv", CustomMath4.isPositive(10));
    assertFalse("Numărul nu ar trebui să fie pozitiv", CustomMath4.isPositive(-5));
    assertFalse("Zero nu este pozitiv", CustomMath4.isPositive(0));
  }
}
