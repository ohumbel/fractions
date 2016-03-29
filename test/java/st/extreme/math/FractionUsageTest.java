package st.extreme.math;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class FractionUsageTest {

  @Test
  public void testSamisUseCase() {
    Quotient quantity = Quotient.valueOf(23456);
    Quotient quantityOrigin = Quotient.valueOf(34567);
    Quotient result = Quotient.valueOf(1000);

    Quotient factor = quantity.divide(quantityOrigin);
    assertEquals("23456/34567", factor.toString());
    result = result.multiply(factor);
    assertEquals("23456000/34567", result.toString());

    Quotient factor2 = quantityOrigin.divide(quantity);
    assertEquals("34567/23456", factor2.toString());
    result = result.multiply(factor2);
    assertEquals("810803552000/810803552", result.toString());
    assertEquals(new BigDecimal("1000"), result.bigDecimalValue());
    assertEquals("1000", result.toPlainString());
  }

}
