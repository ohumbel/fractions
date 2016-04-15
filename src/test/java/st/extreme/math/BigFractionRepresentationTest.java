package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BigFractionRepresentationTest {

  @Test
  public void testToFractionString() {
    BigFraction q;
    q = new BigFraction("3", "4");
    assertEquals("3/4", q.toFractionString());
    q = new BigFraction("-3", "4");
    assertEquals("-3/4", q.toFractionString());
    q = new BigFraction("3", "-4");
    assertEquals("-3/4", q.toFractionString());
    q = new BigFraction("-3", "-4");
    assertEquals("3/4", q.toFractionString());
  }

  @Test
  public void testToFractionString_DenominatorOne() {
    BigFraction q;
    q = new BigFraction("8", "4");
    assertEquals("2", q.toFractionString());
    q = new BigFraction("-8", "4");
    assertEquals("-2", q.toFractionString());
    q = new BigFraction("8", "-4");
    assertEquals("-2", q.toFractionString());
    q = new BigFraction("-8", "-4");
    assertEquals("2", q.toFractionString());
  }

  @Test
  public void testToString() {
    BigFraction q;
    q = new BigFraction("3", "4");
    assertEquals("0.75", q.toString());
    q = new BigFraction("-3", "4");
    assertEquals("-0.75", q.toString());
    q = new BigFraction("3", "-4");
    assertEquals("-0.75", q.toString());
    q = new BigFraction("-3", "-4");
    assertEquals("0.75", q.toString());
  }

  @Test
  public void testToString_DenominatorOne() {
    BigFraction q;
    q = new BigFraction("8", "4");
    assertEquals("2", q.toString());
    q = new BigFraction("-8", "4");
    assertEquals("-2", q.toString());
    q = new BigFraction("8", "-4");
    assertEquals("-2", q.toString());
    q = new BigFraction("-8", "-4");
    assertEquals("2", q.toString());
  }

  @Test
  public void testToString_periodical() {
    BigFraction q = new BigFraction("1", "7");
    String period = "142857";
    StringBuilder expected = new StringBuilder();
    expected.append("0.");
    for (int i = 0; i < 20; i++) {
      expected.append(period);
    }
    assertTrue(q.toString().startsWith(expected.toString()));
  }
}
