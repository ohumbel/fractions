package st.extreme.math;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Ignore;
import org.junit.Test;

public class BigFractionUsageTest {

  @Test
  public void testSamisUseCase() {
    BigFraction quantity = BigFraction.valueOf(23456);
    BigFraction quantityOrigin = BigFraction.valueOf(34567);
    BigFraction result = BigFraction.valueOf(1000);

    BigFraction factor = quantity.divide(quantityOrigin);
    assertEquals("23456/34567", factor.toFractionString());
    result = result.multiply(factor);
    assertEquals("23456000/34567", result.toFractionString());

    BigFraction factor2 = quantityOrigin.divide(quantity);
    assertEquals("34567/23456", factor2.toFractionString());
    result = result.multiply(factor2);
    assertEquals("1000", result.toFractionString());
    assertEquals("1000", result.toString());
    assertEquals(BigFraction.valueOf("1000"), result);
  }

  @Test
  public void testReadmeValue() {
    BigFraction readmeValue = new BigFraction("123456789", "10000");
    assertEquals("12345.6789", readmeValue.toString());
  }

  @Test
  public void testUseCase1() {
    BigFraction start = BigFraction.valueOf("1000");
    BigFraction divisor = BigFraction.valueOf("21");
    BigFraction quotient = start.divide(divisor);
    BigFraction result = quotient.multiply(divisor);
    assertEquals("1000", result.toFractionString());
    assertEquals("1000", result.toString());
    assertEquals(start, result);
  }

  @Test
  @Ignore
  public void testUseCase1_BigDecimal() {
    BigDecimal start = new BigDecimal("1000");
    BigDecimal divisor = new BigDecimal("21");
    BigDecimal quotient = start.divide(divisor, new MathContext(30, RoundingMode.HALF_UP));
    BigDecimal result = quotient.multiply(divisor);
    assertEquals("1000", result.toPlainString());
    assertEquals(start, result);
  }

  @Test
  public void testUseCase2_multiply() {
    BigFraction bf1 = BigFraction.valueOf("2/3");
    BigFraction bf2 = BigFraction.valueOf("-6/7");
    BigFraction result = bf1.multiply(bf2);
    assertEquals("-4/7", result.toFractionString());
  }

  @Test
  public void testUseCase2_divide() {
    BigFraction bf1 = BigFraction.valueOf("2/3");
    BigFraction bf2 = BigFraction.valueOf("6/7");
    BigFraction result = bf1.divide(bf2);
    assertEquals("7/9", result.toFractionString());
  }

  @Test
  public void testUseCase2_add() {
    BigFraction bf1 = BigFraction.valueOf("2/15");
    BigFraction bf2 = BigFraction.valueOf("6/5");
    BigFraction result = bf1.add(bf2);
    assertEquals("4/3", result.toFractionString());
  }

  @Test
  public void testUseCase2_subtract() {
    BigFraction bf1 = BigFraction.valueOf("8/15");
    BigFraction bf2 = BigFraction.valueOf("6/5");
    BigFraction result = bf1.subtract(bf2);
    assertEquals("-2/3", result.toFractionString());
  }

  @Test
  public void testUseCase2_pow() {
    BigFraction bf1 = BigFraction.valueOf("-2/3");
    BigFraction result = bf1.pow(-3);
    assertEquals("-27/8", result.toFractionString());
  }
}
