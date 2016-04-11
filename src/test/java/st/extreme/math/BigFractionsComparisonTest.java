package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

}
