package st.extreme.math;

import static org.junit.Assert.assertEquals;
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
    assertEquals(0, q.signum());

    q = BigFraction.valueOf("1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(1, q.signum());

    q = BigFraction.valueOf("+1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(1, q.signum());

    q = BigFraction.valueOf("-1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(-1, q.signum());
  }

  @Test
  public void testValueOf_noString() {
    BigFraction q = BigFraction.valueOf("");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(0, q.signum());

    q = BigFraction.valueOf((String) null);
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(0, q.signum());
  }

  @Test
  public void testValueOf_Signs() {
    BigFraction q = BigFraction.valueOf("-1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(-1, q.signum());

    q = BigFraction.valueOf("+1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(1, q.signum());
  }

  @Test
  public void testValueOf_WithDecimals() {
    BigFraction q = BigFraction.valueOf("1.5");
    assertEquals(new BigInteger("3"), q.getNumerator());
    assertEquals(new BigInteger("2"), q.getDenominator());
    assertEquals(1, q.signum());

    q = BigFraction.valueOf("-1.5");
    assertEquals(new BigInteger("-3"), q.getNumerator());
    assertEquals(new BigInteger("2"), q.getDenominator());
    assertEquals(-1, q.signum());

    q = BigFraction.valueOf("-1234.5678");
    assertEquals(new BigInteger("-6172839"), q.getNumerator());
    assertEquals(new BigInteger("5000"), q.getDenominator());
    assertEquals(-1, q.signum());

    // maybe we could eliminate the same number of trailing zeroes
    q = BigFraction.valueOf("1.000");
    assertEquals(new BigInteger("1"), q.getNumerator());
    assertEquals(new BigInteger("1"), q.getDenominator());
    assertEquals(1, q.signum());

    q = BigFraction.valueOf("-0.00000001");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(new BigInteger("100000000"), q.getDenominator());
    assertEquals(-1, q.signum());

    q = BigFraction.valueOf("-0.000");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(new BigInteger("1"), q.getDenominator());
    assertEquals(0, q.signum());

    q = BigFraction.valueOf("+0.000");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(new BigInteger("1"), q.getDenominator());
    assertEquals(0, q.signum());
  }

  @Test
  public void testValueOf_MinimalInput() {
    BigFraction q;

    q = BigFraction.valueOf("-1");
    assertEquals(new BigInteger("-1"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(-1, q.signum());

    q = BigFraction.valueOf("+1");
    assertEquals(BigInteger.ONE, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(1, q.signum());
  }

  @Test
  public void testValueOf_ZeroOutput() {
    BigFraction q;

    q = BigFraction.valueOf("0");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(0, q.signum());

    q = BigFraction.valueOf("");
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(0, q.signum());

    q = BigFraction.valueOf((String) null);
    assertEquals(BigInteger.ZERO, q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(0, q.signum());
  }

  @Test
  public void testValueOf_IllegalInput() {
    assertNumberFormatException("x");
    assertNumberFormatException(".");
    assertNumberFormatException("-");
    assertNumberFormatException("+");
    assertNumberFormatException(".-");
    assertNumberFormatException(".+");
    assertNumberFormatException("+.");
    assertNumberFormatException("-.");
    assertNumberFormatException("1x");
    assertNumberFormatException("1.");
    assertNumberFormatException(".1");
    assertNumberFormatException(".2-");
    assertNumberFormatException(".3+");
    assertNumberFormatException("+.1");
    assertNumberFormatException("-.1");
    assertNumberFormatException("+1.");
    assertNumberFormatException("-1.");
    assertNumberFormatException("-0");
    assertNumberFormatException("+0");
    assertNumberFormatException("000");
    assertNumberFormatException("001");
    assertNumberFormatException("+001");
    assertNumberFormatException("-001");
  }

  @Test
  public void testValueOf_Fraction() {
    BigFraction q;

    q = BigFraction.valueOf("2/3");
    assertEquals(new BigInteger("2"), q.getNumerator());
    assertEquals(new BigInteger("3"), q.getDenominator());
    assertEquals(1, q.signum());

    q = BigFraction.valueOf("-2/3");
    assertEquals(new BigInteger("-2"), q.getNumerator());
    assertEquals(new BigInteger("3"), q.getDenominator());
    assertEquals(-1, q.signum());

    q = BigFraction.valueOf("2/-3");
    assertEquals(new BigInteger("-2"), q.getNumerator());
    assertEquals(new BigInteger("3"), q.getDenominator());
    assertEquals(-1, q.signum());

    q = BigFraction.valueOf("-2/-3");
    assertEquals(new BigInteger("2"), q.getNumerator());
    assertEquals(new BigInteger("3"), q.getDenominator());
    assertEquals(1, q.signum());
  }

  @Test
  public void testValueOf_Fraction_IllegalInput() {
    assertNumberFormatException("a/b");
    assertNumberFormatException("2/");
    assertNumberFormatException("/3");
    assertNumberFormatException("02/03");
    assertNumberFormatException("-02/+03");
    assertNumberFormatException("2.5/");
    assertNumberFormatException("/3.7");
    assertNumberFormatException("/");
    assertNumberFormatException(".");
    assertNumberFormatException("-/+");
    assertNumberFormatException("1/0");
    assertNumberFormatException("-1/0");
    assertNumberFormatException("+1/0");
    assertNumberFormatException("0/0");
    assertNumberFormatException("+0/1");
    assertNumberFormatException("-0/1");
  }

  @Test
  public void testValueOf_Fraction_DivisionByZero() {
  }

  @Test
  public void testValueOf_Long() {
    Long longVal = Long.valueOf(-123456789012345L);
    BigFraction q = BigFraction.valueOf(longVal);
    assertEquals(new BigInteger("-123456789012345"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(-1, q.signum());
  }

  @Test
  public void testValueOf_Double() {
    Double doubleVal = Double.valueOf("-1.75");
    BigFraction q = BigFraction.valueOf(doubleVal);
    assertEquals(new BigInteger("-7"), q.getNumerator());
    assertEquals(new BigInteger("4"), q.getDenominator());
    assertEquals(-1, q.signum());

    doubleVal = Double.valueOf(doubleVal.doubleValue() / 250.0);
    q = BigFraction.valueOf(doubleVal);
    assertEquals(new BigInteger("-7"), q.getNumerator());
    assertEquals(new BigInteger("1000"), q.getDenominator());
    assertEquals(-1, q.signum());
  }

  @Test
  public void testValueOf_int() {
    BigFraction q = BigFraction.valueOf(7);
    assertEquals(new BigInteger("7"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(1, q.signum());
  }

  @Test
  public void testValueOf_long() {
    BigFraction q = BigFraction.valueOf(-7L);
    assertEquals(new BigInteger("-7"), q.getNumerator());
    assertEquals(BigInteger.ONE, q.getDenominator());
    assertEquals(-1, q.signum());
  }

  @Test
  public void testValueOf_float() {
    BigFraction q = BigFraction.valueOf(-1.85493F);
    assertEquals(new BigInteger("-185493"), q.getNumerator());
    assertEquals(new BigInteger("100000"), q.getDenominator());
    assertEquals(-1, q.signum());
  }

  @Test
  public void testValueOf_double() {
    BigFraction q = BigFraction.valueOf(-1.8549399020399203D);
    assertEquals(new BigInteger("-9274699510199601"), q.getNumerator());
    assertEquals(new BigInteger("5000000000000000"), q.getDenominator());
    assertEquals(-1, q.signum());
  }

  @Test
  public void testValueOf_BigDecimal() {
    BigDecimal bigDecimal = new BigDecimal("-847292.1120022");
    BigFraction q = BigFraction.valueOf(bigDecimal);
    assertEquals(new BigInteger("-4236460560011"), q.getNumerator());
    assertEquals(new BigInteger("5000000"), q.getDenominator());
    assertEquals(-1, q.signum());
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
