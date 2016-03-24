package st.extreme.math;

public class Quotient {

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

	private String numerator;
	private String denominator;
	private boolean positive;

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
	 * Create a new quotient from a string input
	 * 
	 * @param number
	 *            in the format [sign][digits].[digits]
	 * 
	 * @return a quotient representing the passed in number value
	 */
	public static Quotient valueOf(String number) {
		if (number == null || number.isEmpty() || ZERO.equals(number)) {
			return new Quotient(ZERO, ONE);
		}
		char[] chars = number.toCharArray();
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
					throw new NumberFormatException("illegal character ".concat(String.valueOf(c))
							.concat(" at position ").concat(String.valueOf(pos)));
				}
				break;
			case DOT_CHAR:
				beforeDecimal = false;
				break;
			case D0_CHAR:
				if (numerator.length() == 0 && beforeDecimal) {
					// skip leading zeroes
					break;
				}
				// intentially no break
			case D1_CHAR:
			case D2_CHAR:
			case D3_CHAR:
			case D4_CHAR:
			case D5_CHAR:
			case D6_CHAR:
			case D7_CHAR:
			case D8_CHAR:
			case D9_CHAR:
				numerator.append(c);
				if (!beforeDecimal) {
					denominator.append(ZERO);
				}
				break;
			default:
				throw new NumberFormatException("illegal character ".concat(String.valueOf(c)).concat(" at position ")
						.concat(String.valueOf(pos)));
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
}