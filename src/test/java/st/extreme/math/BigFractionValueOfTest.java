package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class BigFractionValueOfTest {

  @Test
  public void testValueOf_Integer() {
    BigFraction q = BigFraction.valueOf("0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_noString() {
    BigFraction q = BigFraction.valueOf("");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf((String) null);
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());
  }

  @Test
  public void testValueOf_Signs() {
    BigFraction q = BigFraction.valueOf("-1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());

    q = BigFraction.valueOf("+1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());
  }

  @Test
  public void testValueOf_LeadingZeroes() {
    BigFraction q = BigFraction.valueOf("000");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("001");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+001");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-001");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());

    q = BigFraction.valueOf("-0.00000001");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(new BigInteger("100000000"), q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_WithDecimals() {
    BigFraction q = BigFraction.valueOf("1.5");
    assertEquals(new BigInteger("15"), q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-1.5");
    assertEquals(new BigInteger("-15"), q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator());
    assertFalse(q.isPositive());

    q = BigFraction.valueOf("-1234.5678");
    assertEquals(new BigInteger("-12345678"), q.getNumerator());
    assertEquals(new BigInteger("10000"), q.getDenominator());
    assertFalse(q.isPositive());

    // maybe we could eliminate the same number of trailing zeroes
    q = BigFraction.valueOf("1.000");
    assertEquals(new BigInteger("1000"), q.getNumerator());
    assertEquals(new BigInteger("1000"), q.getDenominator());
    assertTrue(q.isPositive());
  }

  @Test
  public void testValueOf_MinimalInput() {
    BigFraction q = BigFraction.valueOf(".5");
    assertEquals(new BigInteger("5"), q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf(".");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf(".0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator()); // ok
    assertTrue(q.isPositive());

    q = BigFraction.valueOf(".1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("0.");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("1.");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());

    q = BigFraction.valueOf("+");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+.");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+.0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator()); // ok
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+.1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+0.");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("+1.");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-.");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-.0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator()); // ok
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-.1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(new BigInteger("10"), q.getDenominator());
    assertFalse(q.isPositive());

    q = BigFraction.valueOf("-0.");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());

    q = BigFraction.valueOf("-1.");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_IllegalInput() {
    assertNumberFormatException("x");
    assertNumberFormatException(".-");
    assertNumberFormatException(".+");
    assertNumberFormatException("1x");
    assertNumberFormatException(".2-");
    assertNumberFormatException(".3+");
  }

  @Test
  public void testValueOf_Long() {
    Long longVal = Long.valueOf(-123456789012345L);
    BigFraction q = BigFraction.valueOf(longVal);
    assertEquals(new BigInteger("-123456789012345"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_Double() {
    Double doubleVal = Double.valueOf("-1.75");
    BigFraction q = BigFraction.valueOf(doubleVal);
    assertEquals(new BigInteger("-175"), q.getNumerator());
    assertEquals(new BigInteger("100"), q.getDenominator());
    assertFalse(q.isPositive());

    doubleVal = Double.valueOf(doubleVal.doubleValue() / 250.0);
    q = BigFraction.valueOf(doubleVal);
    assertEquals(new BigInteger("-7"), q.getNumerator());
    assertEquals(new BigInteger("1000"), q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_int() {
    BigFraction q = BigFraction.valueOf(7);
    assertEquals(new BigInteger("7"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertTrue(q.isPositive());
  }

  @Test
  public void testValueOf_long() {
    BigFraction q = BigFraction.valueOf(-7L);
    assertEquals(new BigInteger("-7"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_float() {
    BigFraction q = BigFraction.valueOf(-1.85493F);
    assertEquals(new BigInteger("-185493"), q.getNumerator());
    assertEquals(new BigInteger("100000"), q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_double() {
    BigFraction q = BigFraction.valueOf(-1.8549399020399203D);
    assertEquals(new BigInteger("-18549399020399202"), q.getNumerator());
    assertEquals(new BigInteger("10000000000000000"), q.getDenominator());
    assertFalse(q.isPositive());
  }

  @Test
  public void testValueOf_BigDecimal() {
    BigDecimal bigDecimal = new BigDecimal("-847292.1120022");
    BigFraction q = BigFraction.valueOf(bigDecimal);
    assertEquals(new BigInteger("-8472921120022"), q.getNumerator());
    assertEquals(new BigInteger("10000000"), q.getDenominator());
    assertFalse(q.isPositive());
  }

  private void assertNumberFormatException(String input) {
    try {
      BigFraction.valueOf(input);
      fail("NumberFormatException expected");
    } catch (NumberFormatException nfe) {
      // ok
    }
  }

}
