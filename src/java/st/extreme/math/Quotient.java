package st.extreme.math;

public class Quotient {

	private static final String ONE = "1";

	private String numerator;
	private String denominator;

	Quotient(String numerator, String denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public Quotient reciprocal() {
		return new Quotient(denominator, numerator);
	}

	public String getNumerator() {
		return numerator;
	}

	public String getDenominator() {
		return denominator;
	}

	public String toString() {
		return numerator.concat("/").concat(denominator);
	}

	public static Quotient fromString(String number) {
		return new Quotient(number, ONE);
	}
}