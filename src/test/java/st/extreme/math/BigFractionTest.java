package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

public class BigFractionTest {

  @Test
  public void testReciprocal() {
    BigFraction q = new BigFraction("3", "4");
    BigFraction r = q.reciprocal();
    assertEquals(new BigInteger("4"), r.getNumerator());
    assertEquals(new BigInteger("3"), r.getDenominator());
    assertEquals(q.signum(), r.signum());
  }

  @Test
  public void testReciprocal_Negative() {
    BigFraction q = new BigFraction("-3", "4");
    BigFraction r = q.reciprocal();
    assertEquals(new BigInteger("-4"), r.getNumerator());
    assertEquals(new BigInteger("3"), r.getDenominator()); // we always keep the denominator positive
    assertEquals(q.signum(), r.signum());
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
  public void testSignum() {
    assertEquals(0, new BigFraction("0", "10").signum());
    assertEquals(1, new BigFraction("10", "10").signum());
    assertEquals(-1, new BigFraction("-10", "10").signum());
  }
}
