package st.extreme.math;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quotient implements Comparable<Quotient>, Serializable {

	private static final MathContext MATH_CONTEXT = new MathContext(500, RoundingMode.HALF_UP);
	private static final Pattern INTEGER_PATTERN = Pattern.compile("(0|([1-9][0-9]*))");
	private static final long serialVersionUID = 6724599299245677524L;

	private static final String ONE = "1";
	private static final String ZERO = "0";

	private static final char MINUS_CHAR = '-';
	private static final char PLUS_CHAR = '+';
	private static final char DOT_CHAR = '.';
	private static final char D0_CHAR = '0';
	private static final char D1_CHAR = '1';
	private static final char D2_CHAR = '2';
	private static final char D3_CHAR = '3';
	private static final char D4_CHAR = '4';
	private static final char D5_CHAR = '5';
	private static final char D6_CHAR = '6';
	private static final char D7_CHAR = '7';
	private static final char D8_CHAR = '8';
	private static final char D9_CHAR = '9';

	private final String numerator;
	private final String denominator;
	private final boolean positive;

	private transient BigDecimal bigDecimalValue;

	/**
	 * create a positive quotient
	 * 
	 * @param numerator
	 * @param denominator
	 */
	Quotient(String numerator, String denominator) {
		this(numerator, denominator, true);
	}

	/**
	 * create a quotient with a sign
	 * 
	 * @param numerator
	 * @param denominator
	 * @param positive
	 */
	Quotient(String numerator, String denominator, boolean positive) {
		Matcher numeratorMatcher = INTEGER_PATTERN.matcher(numerator);
		if (!numeratorMatcher.matches()) {
			throw new NumberFormatException(buildNumberFormatExceptionMessage(numerator));
		}
		Matcher denominatorMatcher = INTEGER_PATTERN.matcher(denominator);
		if (!denominatorMatcher.matches()) {
			throw new NumberFormatException(buildNumberFormatExceptionMessage(numerator));
		}
		this.numerator = numerator;
		this.denominator = denominator;
		this.positive = positive;
	}

	/**
	 * Create a quotient with the reciprocal value
	 * 
	 * @return a new quotient with the reciprocal value of this quotient
	 */
	public Quotient reciprocal() {
		return new Quotient(denominator, numerator);
	}

	public String getNumerator() {
		return numerator;
	}

	public String getDenominator() {
		return denominator;
	}

	public boolean isPositive() {
		return positive;
	}

	/**
	 * @return a human readable representation
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (!isPositive()) {
			builder.append(MINUS_CHAR);
		}
		builder.append(numerator);
		builder.append('/');
		builder.append(denominator);
		return builder.toString();
	}

	/**
	 * Create a new quotient from an int input
	 */
	public static Quotient valueOf(int i) {
		return valueOf(Integer.valueOf(i));
	}

	/**
	 * Create a new quotient from a long input
	 */
	public static Quotient valueOf(long l) {
		return valueOf(Long.valueOf(l));
	}

	/**
	 * Create a new quotient from a double input
	 */
	public static Quotient valueOf(double d) {
		return valueOf(Double.valueOf(d));
	}

	/**
	 * Create a new quotient from a float input
	 */
	public static Quotient valueOf(float f) {
		return valueOf(Float.valueOf(f));
	}

	/**
	 * Create a new quotient from {@link Number} input
	 */
	public static Quotient valueOf(Number number) {
		return valueOf(number.toString());
	}

	/**
	 * Create a new quotient from {@link BigDecimal} input
	 */
	public static Quotient valueOf(BigDecimal bigDecimal) {
		return valueOf(bigDecimal.toPlainString());
	}

	/**
	 * Create a new quotient from a {@link String} input
	 * 
	 * @param numberString
	 *            in the format [sign][digits].[digits]
	 * 
	 * @return a quotient representing the passed in numeric value
	 */
	public static Quotient valueOf(String numberString) {
		if (numberString == null || numberString.isEmpty() || ZERO.equals(numberString)) {
			return new Quotient(ZERO, ONE);
		}
		// TODO use regex to validate?
		char[] chars = numberString.toCharArray();
		boolean isPositive = true;
		StringBuilder numerator = new StringBuilder();
		StringBuilder denominator = new StringBuilder();
		denominator.append(ONE);
		int pos = 0;
		boolean beforeDecimal = true;
		for (char c : chars) {
			switch (c) {
			case MINUS_CHAR:
				isPositive = false;
				// intentially no break
			case PLUS_CHAR:
				if (pos > 0) {
					throw new NumberFormatException(buildNumberFormatExceptionMessage(numberString, pos, c));
				}
				break;
			case DOT_CHAR:
				beforeDecimal = false;
				break;
			case D0_CHAR:
			case D1_CHAR:
			case D2_CHAR:
			case D3_CHAR:
			case D4_CHAR:
			case D5_CHAR:
			case D6_CHAR:
			case D7_CHAR:
			case D8_CHAR:
			case D9_CHAR:
				if (!beforeDecimal) {
					denominator.append(ZERO);
				}
				// skip leading zeroes in numerator
				if (numerator.length() > 0 || D0_CHAR != c) {
					numerator.append(c);
				}
				break;
			default:
				throw new NumberFormatException(buildNumberFormatExceptionMessage(numberString, pos, c));
			}
			pos++;
		}
		if (numerator.length() == 0) {
			numerator.append(ZERO);
		}
		if (ZERO.equals(numerator.toString())) {
			return new Quotient(numerator.toString(), ONE, isPositive);
		} else {
			return new Quotient(numerator.toString(), denominator.toString(), isPositive);
		}
	}

	public BigDecimal bigDecimalValue() {
		// because of immutability, we can lazily evaluate the big decimal value
		if (bigDecimalValue == null) {
			String signedNumerator;
			if (isPositive()) {
				signedNumerator = numerator;
			} else {
				signedNumerator = String.valueOf(MINUS_CHAR).concat(numerator);
			}
			bigDecimalValue = new BigDecimal(signedNumerator).divide(new BigDecimal(denominator), MATH_CONTEXT);
		}
		return bigDecimalValue;
	}

	@Override
	public int compareTo(Quotient other) {
		return bigDecimalValue().compareTo(other.bigDecimalValue());
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Quotient)) {
			return false;
		} else {
			return compareTo((Quotient) object) == 0;
		}
	}

	@Override
	public int hashCode() {
		return bigDecimalValue().hashCode();
	}

	public Quotient multiply(Quotient q) {
		// first naive implementation using BigInteger
		String multipliedNumerator = new BigInteger(numerator).multiply(new BigInteger(q.getNumerator())).toString();
		String multipliedDenominator = new BigInteger(denominator).multiply(new BigInteger(q.getDenominator()))
				.toString();
		return new Quotient(multipliedNumerator, multipliedDenominator, isPositive() && q.isPositive());
	}

	public Quotient divide(Quotient q) {
		return multiply(q.reciprocal());
	}

	private static String buildNumberFormatExceptionMessage(String numberString, int pos, char c) {
		return "'".concat(numberString).concat("': illegal character ").concat(String.valueOf(c))
				.concat(" at position ").concat(String.valueOf(pos));
	}

	private static String buildNumberFormatExceptionMessage(String numberString) {
		return "illegal number '".concat(numberString).concat("'");
	}

}