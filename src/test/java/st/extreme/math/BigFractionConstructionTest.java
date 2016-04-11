package st.extreme.math;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BigFractionConstructionTest {

  @Test
  public void testConstructor_IllegalCharacter() {
    try {
      new BigFraction(".", "4");
      fail("NumberFormatException expected");
    } catch (NumberFormatException nfe) {
      // ok
    }
    try {
      new BigFraction("4", ".");
      fail("NumberFormatException expected");
    } catch (NumberFormatException nfe) {
      // ok
    }
  }

  @Test
  public void testConstructor_divisionByZero() {
    try {
      new BigFraction("1", "0");
      fail("ArithmeticException expected");
    } catch (ArithmeticException ae) {
      assertTrue(ae.getMessage().contains("division by zero"));
    }
  }

}
