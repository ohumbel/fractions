package st.extreme.math;

import static org.junit.Assert.assertEquals;

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
}
