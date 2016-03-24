package st.extreme.math;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class QuotientTest {

	@Test
	public void testReciprocal() {
		Quotient q = new Quotient("3", "4");
		Quotient r = q.reciprocal();
		assertEquals("4", r.getNumerator());
		assertEquals("3", r.getDenominator());
	}

	@Test
	public void testToString() {
		Quotient q = new Quotient("3", "4");
		assertEquals("3/4", q.toString());
	}

	@Test
	public void testFromString_Integer() {
		Quotient q = Quotient.fromString("0");
		assertEquals("0", q.getNumerator());
		assertEquals("1", q.getDenominator());
		q = Quotient.fromString("1");
		assertEquals("1", q.getNumerator());
		assertEquals("1", q.getDenominator());
		q = Quotient.fromString("-1");
		assertEquals("-1", q.getNumerator());
		assertEquals("1", q.getDenominator());
	}

	@Test
	@Ignore
	public void testFromString_WithDecimals() {
		fail("implement");
	}
}
