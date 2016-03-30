package st.extreme.math;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class FractionUsageTest {

  @Test
  public void testSamisUseCase() {
    Fraction quantity = Fraction.valueOf(23456);
    Fraction quantityOrigin = Fraction.valueOf(34567);
    Fraction result = Fraction.valueOf(1000);

    Fraction factor = quantity.divide(quantityOrigin);
    assertEquals("23456/34567", factor.toFractionString());
    result = result.multiply(factor);
    assertEquals("23456000/34567", result.toFractionString());

    Fraction factor2 = quantityOrigin.divide(quantity);
    assertEquals("34567/23456", factor2.toFractionString());
    result = result.multiply(factor2);
    assertEquals("810803552000/810803552", result.toFractionString());
    assertEquals(new BigDecimal("1000"), result.bigDecimalValue());
    assertEquals("1000", result.toString());
  }

  @Test
  public void testReadmeValue() {
    Fraction readmeValue = new Fraction("123456789", "10000");
    assertEquals("12345.6789", readmeValue.toString());
  }
}
