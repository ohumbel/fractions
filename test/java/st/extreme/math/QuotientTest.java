package st.extreme.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

public class QuotientTest {

	@Test
	public void testReciprocal() {
		Quotient q = new Quotient("3", "4");
		Quotient r = q.reciprocal();
		assertEquals("4", r.getNumerator());
		assertEquals("3", r.getDenominator());
		assertEquals(q.isPositive(), r.isPositive());
	}

	@Test
	public void testToString() {
		Quotient q = new Quotient("3", "4");
		assertEquals("3/4", q.toString());
		q = new Quotient("3", "4", false);
		assertEquals("-3/4", q.toString());
	}

	@Test
	public void testValueOf_Integer() {
		Quotient q = Quotient.valueOf("0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("-1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_noString() {
		Quotient q = Quotient.valueOf("");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf((String) null);
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());
	}

	@Test
	public void testValueOf_Signs() {
		Quotient q = Quotient.valueOf("-1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("+1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());
	}

	@Test
	public void testValueOf_LeadingZeroes() {
		Quotient q = Quotient.valueOf("000");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("001");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+001");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("-001");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-0.00000001");
		assertEquals("1", q.getNumerator());
		assertEquals("100000000", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_WithDecimals() {
		Quotient q = Quotient.valueOf("1.5");
		assertEquals("15", q.getNumerator());
		assertEquals("10", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("-1.5");
		assertEquals("15", q.getNumerator());
		assertEquals("10", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-1234.5678");
		assertEquals("12345678", q.getNumerator());
		assertEquals("10000", q.getDenominator());
		assertFalse(q.isPositive());

		// maybe we could eliminate the same number of trailing zeroes
		q = Quotient.valueOf("1.000");
		assertEquals("1000", q.getNumerator());
		assertEquals("1000", q.getDenominator());
		assertTrue(q.isPositive());
	}

	@Test
	public void testValueOf_MinimalInput() {
		Quotient q = Quotient.valueOf(".5");
		assertEquals("5", q.getNumerator());
		assertEquals("10", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf(".");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf(".0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf(".1");
		assertEquals("1", q.getNumerator());
		assertEquals("10", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("0.");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("1.");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("-");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("+");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+.");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+.0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+.1");
		assertEquals("1", q.getNumerator());
		assertEquals("10", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+0.");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("+1.");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());

		q = Quotient.valueOf("-.");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-.0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-.1");
		assertEquals("1", q.getNumerator());
		assertEquals("10", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-0.");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());

		q = Quotient.valueOf("-1.");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_IllegalInput() {
		assertNumberFormatException("x");
		assertNumberFormatException(".-");
		assertNumberFormatException(".+");
		assertNumberFormatException("1x");
		assertNumberFormatException(".2-");
		assertNumberFormatException(".3+");
	}

	@Test
	public void testValueOf_Long() {
		Long longVal = Long.valueOf(-123456789012345L);
		Quotient q = Quotient.valueOf(longVal);
		assertEquals("123456789012345", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_Double() {
		Double doubleVal = Double.valueOf("-1.75");
		Quotient q = Quotient.valueOf(doubleVal);
		assertEquals("175", q.getNumerator());
		assertEquals("100", q.getDenominator());
		assertFalse(q.isPositive());

		doubleVal = Double.valueOf(doubleVal.doubleValue() / 250.0);
		q = Quotient.valueOf(doubleVal);
		assertEquals("7", q.getNumerator());
		assertEquals("1000", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_int() {
		Quotient q = Quotient.valueOf(7);
		assertEquals("7", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertTrue(q.isPositive());
	}

	@Test
	public void testValueOf_long() {
		Quotient q = Quotient.valueOf(-7L);
		assertEquals("7", q.getNumerator());
		assertEquals("1", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_float() {
		Quotient q = Quotient.valueOf(-1.85493F);
		assertEquals("185493", q.getNumerator());
		assertEquals("100000", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_double() {
		Quotient q = Quotient.valueOf(-1.8549399020399203D);
		assertEquals("18549399020399202", q.getNumerator());
		assertEquals("10000000000000000", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testValueOf_BigDecimal() {
		BigDecimal bigDecimal = new BigDecimal("-847292.1120022");
		Quotient q = Quotient.valueOf(bigDecimal);
		assertEquals("8472921120022", q.getNumerator());
		assertEquals("10000000", q.getDenominator());
		assertFalse(q.isPositive());
	}

	@Test
	public void testBigDecimalValue() {
		Quotient q = Quotient.valueOf("-1234.5678");
		assertEquals("-1234.5678", q.bigDecimalValue().toPlainString());

		String numerator = "-12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
		String denominator = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
		String input = numerator.concat(".").concat(denominator);
		q = Quotient.valueOf(input);
		assertEquals(input, q.bigDecimalValue().toPlainString());
	}

	@Test
	public void testCompareTo_equal() {
		Quotient q1 = new Quotient("12", "40", true);
		Quotient q2 = new Quotient("12", "40", true);
		assertEquals(0, q1.compareTo(q2));
		assertEquals(0, q2.compareTo(q1));
		Quotient q3 = new Quotient("12", "40", true);
		assertEquals(0, q1.compareTo(q3));
		assertEquals(0, q2.compareTo(q3));
	}

	@Test
	public void testCompareTo_greater() {
		Quotient q1;
		Quotient q2;
		q1 = new Quotient("13", "40", true);
		q2 = new Quotient("12", "40", true);
		assertTrue(q1.compareTo(q2) > 0);
		q1 = new Quotient("13", "40", false);
		q2 = new Quotient("12", "40", false);
		assertTrue(q2.compareTo(q1) > 0);
	}

	@Test
	public void testCompareTo_less() {
		Quotient q1;
		Quotient q2;
		q1 = new Quotient("13", "40", true);
		q2 = new Quotient("12", "40", true);
		assertTrue(q2.compareTo(q1) < 0);
		q1 = new Quotient("13", "40", false);
		q2 = new Quotient("12", "40", false);
		assertTrue(q1.compareTo(q2) < 0);
	}

	@Test
	public void testEquals() {
		Quotient q1 = new Quotient("12", "40", true);
		Quotient q2 = new Quotient("12", "40", true);
		assertEquals(q1, q2);
		assertEquals(q2, q1);
		Quotient q3 = new Quotient("12", "40", true);
		assertEquals(q1, q3);
		assertEquals(q2, q3);
	}

	@Test
	public void testEquals_notEqual() {
		Quotient q1;
		Quotient q2;
		q1 = new Quotient("12", "40", true);
		q2 = new Quotient("12", "40", false);
		assertNotEquals(q1, q2);
		q2 = new Quotient("12", "41", true);
		assertNotEquals(q1, q2);
		q2 = new Quotient("12", "41", false);
		assertNotEquals(q1, q2);
		q2 = new Quotient("11", "40", true);
		assertNotEquals(q1, q2);
		q2 = new Quotient("11", "40", false);
		assertNotEquals(q1, q2);
	}

	@Test
	public void testHashCode_equal() {
		Quotient q1;
		Quotient q2;
		q1 = new Quotient("7", "8", false);
		q2 = new Quotient("7", "8", false);
		assertEquals(q1.hashCode(), q2.hashCode());
	}

	@Test
	public void testHashCode_multipleCalls() {
		Quotient q1 = new Quotient("94382991", "882932", true);
		int firstHash = q1.hashCode();
		assertEquals(firstHash, q1.hashCode());
		assertEquals(firstHash, q1.hashCode());
	}

	private void assertNumberFormatException(String input) {
		try {
			Quotient.valueOf(input);
			fail("NumberFormatException expected");
		} catch (NumberFormatException nfe) {
			// ok
		}
	}
}
