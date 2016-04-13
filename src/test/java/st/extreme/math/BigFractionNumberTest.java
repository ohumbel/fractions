package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;

public class BigFractionNumberTest {

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
  public void testBigDecimalValue_periodical() {
    BigFraction q = new BigFraction("1", "7");
    String period = "142857";
    StringBuilder expected = new StringBuilder();
    expected.append("0.");
    for (int i = 0; i < 20; i++) {
      expected.append(period);
    }
    assertTrue(q.bigDecimalValue().toPlainString().startsWith(expected.toString()));
  }

  @Test
  public void testBigDecimalValue_MathContext() {
    BigFraction bf = BigFraction.valueOf("8/9");
    MathContext mathContext = new MathContext(10, RoundingMode.DOWN);
    BigDecimal bd = bf.bigDecimalValue(mathContext);
    assertEquals("0.8888888888", bd.toPlainString());
  }

}
