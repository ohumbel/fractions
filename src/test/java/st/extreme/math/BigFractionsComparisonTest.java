package st.extreme.math;

import static org.junit.Assert.assertEquals;
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

    q1 = new BigFraction("2", "4");
    q2 = new BigFraction("3", "6");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("3", "6");
    q2 = new BigFraction("4", "8");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("4", "8");
    q2 = new BigFraction("5", "10");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("5", "10");
    q2 = new BigFraction("6", "12");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("6", "12");
    q2 = new BigFraction("7", "14");
    assertEquals(0, q1.compareTo(q2));

    q1 = new BigFraction("7", "14");
    q2 = new BigFraction("12341234", "24682468");
    assertEquals(0, q1.compareTo(q2));
  }

  @Test
  public void testCompareTo_Number_Decimal() {
    BigFraction bf;
    bf = BigFraction.valueOf("1/4");

    assertEquals(0, bf.compareTo(new BigDecimal("0.25")));
    assertEquals(-1, bf.compareTo(new BigDecimal("0.26")));
    assertEquals(1, bf.compareTo(new BigDecimal("0.24")));

    assertEquals(0, bf.compareTo(Double.valueOf("0.25")));
    assertEquals(0, bf.compareTo(0.25d));
    assertEquals(-1, bf.compareTo(Double.valueOf("0.26")));
    assertEquals(-1, bf.compareTo(0.26d));
    assertEquals(1, bf.compareTo(Double.valueOf("0.24")));
    assertEquals(1, bf.compareTo(0.24d));

    assertEquals(0, bf.compareTo(Float.valueOf("0.25")));
    assertEquals(0, bf.compareTo(0.25f));
    assertEquals(-1, bf.compareTo(Float.valueOf("0.26")));
    assertEquals(-1, bf.compareTo(0.26f));
    assertEquals(1, bf.compareTo(Float.valueOf("0.24")));
    assertEquals(1, bf.compareTo(0.24f));
  }

  @Test
  public void testCompareTo_Number_Integer() {
    BigFraction bf;
    bf = BigFraction.valueOf("4");

    assertEquals(0, bf.compareTo(new BigInteger("4")));
    assertEquals(-1, bf.compareTo(new BigInteger("5")));
    assertEquals(1, bf.compareTo(new BigInteger("3")));

    assertEquals(0, bf.compareTo(Long.valueOf("4")));
    assertEquals(0, bf.compareTo(4L));
    assertEquals(-1, bf.compareTo(Long.valueOf("5")));
    assertEquals(-1, bf.compareTo(5L));
    assertEquals(1, bf.compareTo(Long.valueOf("3")));
    assertEquals(1, bf.compareTo(3L));

    assertEquals(0, bf.compareTo(Integer.valueOf("4")));
    assertEquals(0, bf.compareTo(4));
    assertEquals(-1, bf.compareTo(Integer.valueOf("5")));
    assertEquals(-1, bf.compareTo(5));
    assertEquals(1, bf.compareTo(Integer.valueOf("3")));
    assertEquals(1, bf.compareTo(3));
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
