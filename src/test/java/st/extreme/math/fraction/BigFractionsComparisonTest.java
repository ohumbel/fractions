package st.extreme.math.fraction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;

public class BigFractionsComparisonTest {

  @Test
  public void testCompareTo_equal() {
    BigFraction q1;
    BigFraction q2;

    q1 = new BigFraction("12", "40");
    q2 = new BigFraction("12", "40");
    assertEquals(q1, q1);
    assertEquals(q2, q2);
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
  public void testCompareTo_equal_cancelled() {
    BigFraction q1;
    BigFraction q2;

    q1 = new BigFraction("1", "2");
    q2 = new BigFraction("2", "4");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-1", "2");
    q2 = new BigFraction("-2", "4");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-1", "2");
    q2 = new BigFraction("2", "-4");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("1", "-2");
    q2 = new BigFraction("2", "-4");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("2", "4");
    q2 = new BigFraction("3", "6");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-2", "4");
    q2 = new BigFraction("-3", "6");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-2", "4");
    q2 = new BigFraction("3", "-6");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("2", "-4");
    q2 = new BigFraction("-3", "6");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("2", "-4");
    q2 = new BigFraction("3", "-6");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("3", "6");
    q2 = new BigFraction("4", "8");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-3", "6");
    q2 = new BigFraction("-4", "8");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-3", "6");
    q2 = new BigFraction("4", "-8");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("3", "-6");
    q2 = new BigFraction("-4", "8");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("3", "-6");
    q2 = new BigFraction("4", "-8");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("4", "8");
    q2 = new BigFraction("5", "10");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-4", "8");
    q2 = new BigFraction("-5", "10");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-4", "8");
    q2 = new BigFraction("5", "-10");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("4", "-8");
    q2 = new BigFraction("-5", "10");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("4", "-8");
    q2 = new BigFraction("5", "-10");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("5", "10");
    q2 = new BigFraction("6", "12");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-5", "10");
    q2 = new BigFraction("-6", "12");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-5", "10");
    q2 = new BigFraction("6", "-12");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("5", "-10");
    q2 = new BigFraction("-6", "12");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("5", "-10");
    q2 = new BigFraction("6", "-12");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("6", "12");
    q2 = new BigFraction("7", "14");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-6", "12");
    q2 = new BigFraction("-7", "14");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-6", "12");
    q2 = new BigFraction("7", "-14");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("6", "-12");
    q2 = new BigFraction("-7", "14");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("6", "-12");
    q2 = new BigFraction("7", "-14");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("7", "14");
    q2 = new BigFraction("12341234", "24682468");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-7", "14");
    q2 = new BigFraction("-12341234", "24682468");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("-7", "14");
    q2 = new BigFraction("12341234", "-24682468");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("7", "-14");
    q2 = new BigFraction("-12341234", "24682468");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("7", "-14");
    q2 = new BigFraction("12341234", "-24682468");
    assertEquals(0, q1.compareTo(q2));
  }

  @Test
  public void testCompareTo_equal_bothNumeratorAndDenominatorNegated() {
    BigInteger nominator = new BigInteger("17");
    BigInteger denominator = new BigInteger("29");

    BigFraction q1 = new BigFraction(nominator, denominator);
    BigFraction q2 = new BigFraction(nominator.negate(), denominator.negate());
    assertEquals(0, q1.compareTo(q2));
  }

  @Test
  public void testCompareTo_null() {
    BigFraction bf;
    bf = BigFraction.valueOf("1/4");
    assertNotEquals(0, bf.compareTo(null));
  }

  @Test
  public void testCompareToNumber_Decimal() {
    BigFraction bf;
    bf = BigFraction.valueOf("1/4");

    assertEquals(0, bf.compareToNumber(new BigDecimal("0.25")));
    assertEquals(-1, bf.compareToNumber(new BigDecimal("0.26")));
    assertEquals(1, bf.compareToNumber(new BigDecimal("0.24")));

    assertEquals(0, bf.compareToNumber(Double.valueOf("0.25")));
    assertEquals(0, bf.compareToNumber(0.25d));
    assertEquals(-1, bf.compareToNumber(Double.valueOf("0.26")));
    assertEquals(-1, bf.compareToNumber(0.26d));
    assertEquals(1, bf.compareToNumber(Double.valueOf("0.24")));
    assertEquals(1, bf.compareToNumber(0.24d));

    assertEquals(0, bf.compareToNumber(Float.valueOf("0.25")));
    assertEquals(0, bf.compareToNumber(0.25f));
    assertEquals(-1, bf.compareToNumber(Float.valueOf("0.26")));
    assertEquals(-1, bf.compareToNumber(0.26f));
    assertEquals(1, bf.compareToNumber(Float.valueOf("0.24")));
    assertEquals(1, bf.compareToNumber(0.24f));
  }

  @Test
  public void testCompareToNumber_Integer() {
    BigFraction bf;
    bf = BigFraction.valueOf("4");

    assertEquals(0, bf.compareToNumber(new BigInteger("4")));
    assertEquals(-1, bf.compareToNumber(new BigInteger("5")));
    assertEquals(1, bf.compareToNumber(new BigInteger("3")));

    assertEquals(0, bf.compareToNumber(Long.valueOf("4")));
    assertEquals(0, bf.compareToNumber(4L));
    assertEquals(-1, bf.compareToNumber(Long.valueOf("5")));
    assertEquals(-1, bf.compareToNumber(5L));
    assertEquals(1, bf.compareToNumber(Long.valueOf("3")));
    assertEquals(1, bf.compareToNumber(3L));

    assertEquals(0, bf.compareToNumber(Integer.valueOf("4")));
    assertEquals(0, bf.compareToNumber(4));
    assertEquals(-1, bf.compareToNumber(Integer.valueOf("5")));
    assertEquals(-1, bf.compareToNumber(5));
    assertEquals(1, bf.compareToNumber(Integer.valueOf("3")));
    assertEquals(1, bf.compareToNumber(3));
  }

  @Test
  public void testCompareToNumber_null() {
    BigFraction bf;
    bf = BigFraction.valueOf("1/4");
    assertNotEquals(0, bf.compareToNumber(null));
  }

  @Test
  public void testCompareToNumber_BigFraction() {
    BigFraction q1 = BigFraction.valueOf("1/4");
    BigFraction q2 = BigFraction.valueOf("3/4");

    assertEquals(0, q1.compareToNumber(q1));
    assertEquals(1, q2.compareToNumber(q1));
    assertEquals(-1, q1.compareToNumber(q2));
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
  public void testEquals_null() {
    BigFraction q1 = new BigFraction("3", "4");
    assertFalse(q1.equals(null));
  }

  @Test
  public void testEquals_someCompletelyOtherObject() {
    BigFraction bf = BigFraction.valueOf("1/4");
    assertNotEquals(bf, "0.25");
  }

  @Test
  public void testEquals_Number_shouldAlwaysReturnFalse() {
    BigFraction bf = BigFraction.valueOf("4");
    BigInteger bi = new BigInteger("4");
    assertNotEquals(bf, bi);
    assertNotEquals(bi, bf);
  }

  @Test
  public void testHashCode_equal() {
    BigFraction q1;
    BigFraction q2;
    BigFraction q3;
    q1 = new BigFraction("-8", "7");
    q2 = new BigFraction("-8000", "7000");
    q3 = new BigFraction("-24", "21");
    assertTrue(q1.equals(q2));
    assertTrue(q2.equals(q3));
    assertEquals(q1.hashCode(), q2.hashCode());
    assertEquals(q2.hashCode(), q3.hashCode());
  }

  @Test
  public void testHashCode_multipleCalls() {
    BigFraction q1 = new BigFraction("94382991", "882932");
    int firstHash = q1.hashCode();
    assertEquals(firstHash, q1.hashCode());
    assertEquals(firstHash, q1.hashCode());
    assertEquals(firstHash, q1.hashCode());
  }

}
