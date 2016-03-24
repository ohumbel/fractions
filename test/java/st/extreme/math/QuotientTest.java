package st.extreme.math;

import static org.junit.Assert.*;

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

		q = Quotient.valueOf(null);
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

	private void assertNumberFormatException(String input) {
		try {
			Quotient.valueOf(input);
			fail("NumberFormatException expected");
		} catch (NumberFormatException nfe) {
			// ok
		}
	}
}
