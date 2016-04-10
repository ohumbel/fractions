package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class BigFractionTest {

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

  @Test
  public void testReciprocal() {
    BigFraction q = new BigFraction("3", "4");
    BigFraction r = q.reciprocal();
    assertEquals(new BigInteger("4"), r.getNumerator());
    assertEquals(new BigInteger("3"), r.getDenominator());
    assertEquals(q.isPositive(), r.isPositive());
  }

  @Test
  public void testReciprocal_Negative() {
    BigFraction q = new BigFraction("-3", "4");
    BigFraction r = q.reciprocal();
    assertEquals(new BigInteger("-4"), r.getNumerator());
    assertEquals(new BigInteger("3"), r.getDenominator()); // we always keep the denominator positive
    assertEquals(q.isPositive(), r.isPositive());
  }

  @Test
  public void testReciprocal_divisionByZero() {
    try {
      new BigFraction("1", "0").reciprocal();
      fail("ArithmeticException expected");
    } catch (ArithmeticException ae) {
      assertTrue(ae.getMessage().contains("division by zero"));
    }
  }

  @Test
  public void testToFractionString() {
    BigFraction q = new BigFraction("3", "4");
    assertEquals("3/4", q.toFractionString());
    q = new BigFraction("-3", "4");
    assertEquals("-3/4", q.toFractionString());
    q = new BigFraction("3", "-4");
    assertEquals("-3/4", q.toFractionString());
    q = new BigFraction("-3", "-4");
    assertEquals("3/4", q.toFractionString());
  }

  @Test
  public void testToString() {
    BigFraction q = new BigFraction("3", "4");
    assertEquals("0.75", q.toString());
    q = new BigFraction("-3", "4");
    assertEquals("-0.75", q.toString());
    q = new BigFraction("3", "-4");
    assertEquals("-0.75", q.toString());
    q = new BigFraction("-3", "-4");
    assertEquals("0.75", q.toString());
  }

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

  @Test
  public void testBigDecimalValue() {
    BigFraction q = BigFraction.valueOf("-1234.5678");
    assertEquals("-1234.5678", q.bigDecimalValue().toPlainString());

    String numerator = "-12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
    String denominator = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
    String input = numerator.concat(".").concat(denominator);
    q = BigFraction.valueOf(input);
    assertEquals(input, q.bigDecimalValue().toPlainString());
  }

  @Test
  public void tetsBigDecimalValue_periodic() {
    BigFraction q = new BigFraction("1", "7");
    assertEquals(
        "0.14285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714285714",
        q.bigDecimalValue().toPlainString());
  }

  @Test
  public void testCompareTo_equal() {
    BigFraction q1;
    BigFraction q2;

    q1 = new BigFraction("12", "40");
    q2 = new BigFraction("12", "40");
    assertEquals(0, q1.compareTo(q2));
    assertEquals(0, q2.compareTo(q1));
    BigFraction q3 = new BigFraction("12", "40");
    assertEquals(0, q1.compareTo(q3));
    assertEquals(0, q2.compareTo(q3));

    q1 = new BigFraction("12", "-40");
    q2 = new BigFraction("12", "-40");
    assertEquals(0, q1.compareTo(q2));
    assertEquals(0, q2.compareTo(q1));

    q1 = new BigFraction("-12", "40");
    q2 = new BigFraction("12", "-40");
    assertEquals(0, q1.compareTo(q2));
    assertEquals(0, q2.compareTo(q1));

    q1 = new BigFraction("12", "-40");
    q2 = new BigFraction("-12", "40");
    assertEquals(0, q1.compareTo(q2));
    assertEquals(0, q2.compareTo(q1));

    q1 = new BigFraction("-12", "-40");
    q2 = new BigFraction("-12", "-40");
    assertEquals(0, q1.compareTo(q2));
    assertEquals(0, q2.compareTo(q1));
  }

  @Test
  public void testCompareTo_not_equal() {
    BigFraction q1;
    BigFraction q2;

    q1 = new BigFraction("13", "40");
    q2 = new BigFraction("12", "40");
    assertTrue(q1.compareTo(q2) > 0);
    assertTrue(q2.compareTo(q1) < 0);

    q1 = new BigFraction("-13", "40");
    q2 = new BigFraction("-12", "40");
    assertTrue(q1.compareTo(q2) < 0);
    assertTrue(q2.compareTo(q1) > 0);

    q1 = new BigFraction("13", "-40");
    q2 = new BigFraction("12", "-40");
    assertTrue(q1.compareTo(q2) < 0);
    assertTrue(q2.compareTo(q1) > 0);

    q1 = new BigFraction("-13", "40");
    q2 = new BigFraction("12", "-40");
    assertTrue(q1.compareTo(q2) < 0);
    assertTrue(q2.compareTo(q1) > 0);

    q1 = new BigFraction("13", "-40");
    q2 = new BigFraction("-12", "40");
    assertTrue(q1.compareTo(q2) < 0);
    assertTrue(q2.compareTo(q1) > 0);

    q1 = new BigFraction("-13", "-40");
    q2 = new BigFraction("-12", "-40");
    assertTrue(q1.compareTo(q2) > 0);
    assertTrue(q2.compareTo(q1) < 0);
  }

  @Test
  public void testEquals() {
    BigFraction q1 = new BigFraction("12", "40");
    BigFraction q2 = new BigFraction("12", "40");
    assertEquals(q1, q2);
    assertEquals(q2, q1);
    BigFraction q3 = new BigFraction("12", "40");
    assertEquals(q1, q3);
    assertEquals(q2, q3);
  }

  @Test
  public void testEquals_notEqual() {
    BigFraction q1;
    BigFraction q2;
    q1 = new BigFraction("12", "40");
    q2 = new BigFraction("-12", "40");
    assertNotEquals(q1, q2);
    q2 = new BigFraction("12", "41");
    assertNotEquals(q1, q2);
    q2 = new BigFraction("-12", "41");
    assertNotEquals(q1, q2);
    q2 = new BigFraction("11", "40");
    assertNotEquals(q1, q2);
    q2 = new BigFraction("11", "-40");
    assertNotEquals(q1, q2);
  }

  @Test
  public void testHashCode_equal() {
    BigFraction q1;
    BigFraction q2;
    q1 = new BigFraction("-8", "7");
    q2 = new BigFraction("-8000", "7000");
    assertEquals(q1.hashCode(), q2.hashCode());
  }

  @Test
  public void testHashCode_multipleCalls() {
    BigFraction q1 = new BigFraction("94382991", "882932");
    int firstHash = q1.hashCode();
    assertEquals(firstHash, q1.hashCode());
    assertEquals(firstHash, q1.hashCode());
  }

  @Test
  public void testMultiply() {
    BigFraction q1 = new BigFraction("2", "3");
    BigFraction q2 = new BigFraction("4", "5");
    assertEquals("8/15", q1.multiply(q2).toFractionString());

    q1 = new BigFraction("-3", "7");
    q2 = new BigFraction("8", "9");
    assertEquals("-24/63", q1.multiply(q2).toFractionString());
  }

  @Test
  public void testDivide() {
    BigFraction q1 = new BigFraction("2", "3");
    BigFraction q2 = new BigFraction("4", "5");
    assertEquals("10/12", q1.divide(q2).toFractionString());

    q1 = new BigFraction("3", "-7");
    q2 = new BigFraction("8", "9");
    assertEquals("-27/56", q1.divide(q2).toFractionString());
  }

  @Test
  public void testDivide_divisionByZero() {
    BigFraction q1 = new BigFraction("2", "3");
    BigFraction q2 = new BigFraction("0", "1");
    try {
      q1.divide(q2);
      fail("ArithmeticException expected");
    } catch (ArithmeticException ae) {
      assertTrue(ae.getMessage().contains("division by zero"));
    }
  }

  @Test
  public void testAdd_same_denominator() {
    BigFraction q1;
    BigFraction q2;
    BigFraction result;

    q1 = new BigFraction("2", "3");
    q2 = new BigFraction("3", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("5"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());

    q1 = new BigFraction("-2", "3");
    q2 = new BigFraction("3", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("1"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());

    q1 = new BigFraction("2", "3");
    q2 = new BigFraction("-3", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("-1"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());

    q1 = new BigFraction("-2", "3");
    q2 = new BigFraction("-3", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("-5"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());
  }

  @Test
  public void testAdd() {
    BigFraction q1;
    BigFraction q2;
    BigFraction result;

    q1 = new BigFraction("3", "7");
    q2 = new BigFraction("2", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("23"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());

    q1 = new BigFraction("-3", "7");
    q2 = new BigFraction("2", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("5"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());

    q1 = new BigFraction("3", "7");
    q2 = new BigFraction("-2", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("-5"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());

    q1 = new BigFraction("-3", "7");
    q2 = new BigFraction("-2", "3");
    result = q1.add(q2);
    assertEquals(new BigInteger("-23"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());
  }

  @Test
  public void testSubtract_same_denominator() {
    BigFraction q1;
    BigFraction q2;
    BigFraction result;

    q1 = new BigFraction("3", "3");
    q2 = new BigFraction("2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("1"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());

    q1 = new BigFraction("-3", "3");
    q2 = new BigFraction("2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("-5"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());

    q1 = new BigFraction("3", "3");
    q2 = new BigFraction("-2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("5"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());

    q1 = new BigFraction("-3", "3");
    q2 = new BigFraction("-2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("-1"), result.getNumerator());
    assertEquals(new BigInteger("3"), result.getDenominator());
  }

  @Test
  public void testSubtract() {
    BigFraction q1;
    BigFraction q2;
    BigFraction result;

    q1 = new BigFraction("3", "7");
    q2 = new BigFraction("2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("-5"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());

    q1 = new BigFraction("-3", "7");
    q2 = new BigFraction("2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("-23"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());

    q1 = new BigFraction("3", "7");
    q2 = new BigFraction("-2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("23"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());

    q1 = new BigFraction("-3", "7");
    q2 = new BigFraction("-2", "3");
    result = q1.subtract(q2);
    assertEquals(new BigInteger("5"), result.getNumerator());
    assertEquals(new BigInteger("21"), result.getDenominator());
  }

  @Test
  public void testNegate() {
    BigFraction q;

    q = new BigFraction("4", "3");
    assertEquals(new BigInteger("-4"), q.negate().getNumerator());
    assertEquals(new BigInteger("3"), q.negate().getDenominator());

    q = new BigFraction("-4", "3");
    assertEquals(new BigInteger("4"), q.negate().getNumerator());
    assertEquals(new BigInteger("3"), q.negate().getDenominator());

    q = new BigFraction("4", "-3");
    assertEquals(new BigInteger("4"), q.negate().getNumerator());
    assertEquals(new BigInteger("3"), q.negate().getDenominator());

    q = new BigFraction("-4", "-3");
    assertEquals(new BigInteger("-4"), q.negate().getNumerator());
    assertEquals(new BigInteger("3"), q.negate().getDenominator());
  }

  @Test
  public void testAbs() {
    BigFraction q;

    q = new BigFraction("4", "3");
    assertEquals(new BigInteger("4"), q.abs().getNumerator());
    assertEquals(new BigInteger("3"), q.abs().getDenominator());

    q = new BigFraction("-4", "3");
    assertEquals(new BigInteger("4"), q.abs().getNumerator());
    assertEquals(new BigInteger("3"), q.abs().getDenominator());

    q = new BigFraction("4", "-3");
    assertEquals(new BigInteger("4"), q.abs().getNumerator());
    assertEquals(new BigInteger("3"), q.abs().getDenominator());

    q = new BigFraction("-4", "-3");
    assertEquals(new BigInteger("4"), q.abs().getNumerator());
    assertEquals(new BigInteger("3"), q.abs().getDenominator());
  }

  @Test
  public void testPow_zero_exponent() {
    BigFraction q;

    q = BigFraction.valueOf("0");
    assertEquals(BigInteger.ONE, q.pow(0).getNumerator());
    assertEquals(BigInteger.ONE, q.pow(0).getDenominator());

    q = BigFraction.valueOf("1");
    assertEquals(BigInteger.ONE, q.pow(0).getNumerator());
    assertEquals(BigInteger.ONE, q.pow(0).getDenominator());

    q = BigFraction.valueOf("-1");
    assertEquals(BigInteger.ONE, q.pow(0).getNumerator());
    assertEquals(BigInteger.ONE, q.pow(0).getDenominator());

    q = BigFraction.valueOf("2");
    assertEquals(BigInteger.ONE, q.pow(0).getNumerator());
    assertEquals(BigInteger.ONE, q.pow(0).getDenominator());

    q = BigFraction.valueOf("-2");
    assertEquals(BigInteger.ONE, q.pow(0).getNumerator());
    assertEquals(BigInteger.ONE, q.pow(0).getDenominator());
  }

  @Test
  public void testPow_positive_exponent() {
    BigFraction q;
    BigFraction power;

    q = new BigFraction("1", "4");
    power = q.pow(2);
    assertEquals(BigInteger.ONE, power.getNumerator());
    assertEquals(new BigInteger("16"), power.getDenominator());

    q = new BigFraction("-1", "4");
    power = q.pow(2);
    assertEquals(BigInteger.ONE, power.getNumerator());
    assertEquals(new BigInteger("16"), power.getDenominator());

    q = new BigFraction("2", "3");
    power = q.pow(3);
    assertEquals(new BigInteger("8"), power.getNumerator());
    assertEquals(new BigInteger("27"), power.getDenominator());

    q = new BigFraction("-2", "3");
    power = q.pow(3);
    assertEquals(new BigInteger("-8"), power.getNumerator());
    assertEquals(new BigInteger("27"), power.getDenominator());

    q = new BigFraction("0", "1");
    power = q.pow(2);
    assertEquals(BigInteger.ZERO, power.getNumerator());
    assertEquals(BigInteger.ONE, power.getDenominator());

    q = new BigFraction("0", "1");
    power = q.pow(3);
    assertEquals(BigInteger.ZERO, power.getNumerator());
    assertEquals(BigInteger.ONE, power.getDenominator());
  }

  @Test
  public void testPow_negative_exponent() {
    BigFraction q;
    BigFraction power;

    q = new BigFraction("1", "4");
    power = q.pow(-2);
    assertEquals(new BigInteger("16"), power.getNumerator());
    assertEquals(BigInteger.ONE, power.getDenominator());

    q = new BigFraction("-1", "4");
    power = q.pow(-2);
    assertEquals(new BigInteger("16"), power.getNumerator());
    assertEquals(BigInteger.ONE, power.getDenominator());

    q = new BigFraction("2", "3");
    power = q.pow(-3);
    assertEquals(new BigInteger("27"), power.getNumerator());
    assertEquals(new BigInteger("8"), power.getDenominator());

    q = new BigFraction("-2", "3");
    power = q.pow(-3);
    assertEquals(new BigInteger("-27"), power.getNumerator());
    assertEquals(new BigInteger("8"), power.getDenominator());

    q = new BigFraction("0", "1");
    power = q.pow(-2);
    assertEquals(BigInteger.ZERO, power.getNumerator());
    assertEquals(BigInteger.ONE, power.getDenominator());

    q = new BigFraction("0", "1");
    power = q.pow(-3);
    assertEquals(BigInteger.ZERO, power.getNumerator());
    assertEquals(BigInteger.ONE, power.getDenominator());
  }

  @Test
  public void testIntValue() {
    BigFraction f;
    f = new BigFraction("3", "5");
    assertEquals(0, f.intValue());
    f = new BigFraction("-6", "5");
    assertEquals(-1, f.intValue());
  }

  @Test
  public void testLongValue() {
    BigFraction f;
    f = new BigFraction("26", "5");
    assertEquals(5, f.longValue());
    f = new BigFraction("-24", "5");
    assertEquals(-4, f.longValue());
  }

  @Test
  public void testFloatValue() {
    float epsilon = 0.000001f;
    BigFraction f;
    f = new BigFraction("26", "8");
    assertEquals(Float.valueOf("3.25").floatValue(), f.floatValue(), epsilon);
    f = new BigFraction("-23", "8");
    assertEquals(Float.valueOf("-2.875").floatValue(), f.floatValue(), epsilon);
  }

  @Test
  public void testDoubleValue() {
    double epsilon = 0.000001f;
    BigFraction f;
    f = new BigFraction("26", "8");
    assertEquals(Double.valueOf("3.25").doubleValue(), f.doubleValue(), epsilon);
    f = new BigFraction("-23", "8");
    assertEquals(Double.valueOf("-2.875").doubleValue(), f.doubleValue(), epsilon);
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
