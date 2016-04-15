package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

public class BigFractionConstructionTest {

  private static final BigInteger MINUS_TEN = new BigInteger("-10");
  private static final BigInteger MINUS_FIVE = new BigInteger("-5");
  private static final BigInteger MINUS_THREE = new BigInteger("-3");
  private static final BigInteger MINUS_TWO = new BigInteger("-2");
  private static final BigInteger MINUS_ONE = new BigInteger("-1");
  private static final BigInteger ZERO = new BigInteger("0");
  private static final BigInteger ONE = new BigInteger("1");
  private static final BigInteger TWO = new BigInteger("2");
  private static final BigInteger THREE = new BigInteger("3");
  private static final BigInteger FIVE = new BigInteger("5");
  private static final BigInteger TEN = new BigInteger("10");

  @Test
  public void testStringConstructor_IllegalCharacter() {
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
  public void testStringConstructor_divisionByZero() {
    try {
      new BigFraction("1", "0");
      fail("ArithmeticException expected");
    } catch (ArithmeticException ae) {
      assertTrue(ae.getMessage().contains("division by zero"));
    }
  }

  @Test
  public void testStringConstructor_Sign() {
    BigFraction bf;

    bf = new BigFraction("2", "-3");
    // expect the sign in the numerator
    assertEquals(MINUS_TWO, bf.getNumerator());
    assertEquals(THREE, bf.getDenominator());
    assertEquals(-1, bf.signum());

    bf = new BigFraction("-2", "-3");
    assertEquals(TWO, bf.getNumerator());
    assertEquals(THREE, bf.getDenominator());
    assertEquals(1, bf.signum());
  }

  @Test
  public void testStringConstructor() {
    BigFraction bf = new BigFraction("2", "3");
    assertEquals(TWO, bf.getNumerator());
    assertEquals(THREE, bf.getDenominator());
  }

  @Test
  public void testBigIntegerConstructor() {
    BigFraction bf = new BigFraction(TWO, THREE);
    assertEquals(TWO, bf.getNumerator());
    assertEquals(THREE, bf.getDenominator());
  }

  @Test
  public void testBigIntegerConstructor_divisionByZero() {
    try {
      new BigFraction(ONE, ZERO);
      fail("ArithmeticException expected");
    } catch (ArithmeticException ae) {
      assertTrue(ae.getMessage().contains("division by zero"));
    }
  }

  @Test
  public void testBigIntegerConstructor_Sign() {
    BigFraction bf;

    bf = new BigFraction(TWO, MINUS_THREE);
    // expect the sign in the numerator
    assertEquals(MINUS_TWO, bf.getNumerator());
    assertEquals(THREE, bf.getDenominator());
    assertEquals(-1, bf.signum());

    bf = new BigFraction(MINUS_TWO, MINUS_THREE);
    assertEquals(TWO, bf.getNumerator());
    assertEquals(THREE, bf.getDenominator());
    assertEquals(1, bf.signum());
  }

  @Test
  public void testBigIntegerConstructor_Cancellation() {
    BigFraction bf;

    bf = new BigFraction(FIVE, TEN);
    assertEquals(ONE, bf.getNumerator());
    assertEquals(TWO, bf.getDenominator());
    assertEquals(1, bf.signum());

    bf = new BigFraction(FIVE, MINUS_TEN);
    assertEquals(MINUS_ONE, bf.getNumerator());
    assertEquals(TWO, bf.getDenominator());
    assertEquals(-1, bf.signum());

    bf = new BigFraction(MINUS_FIVE, MINUS_TEN);
    assertEquals(ONE, bf.getNumerator());
    assertEquals(TWO, bf.getDenominator());
    assertEquals(1, bf.signum());
  }

}
