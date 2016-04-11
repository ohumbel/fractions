package st.extreme.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BigFractionRepresentationTest {

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

}
