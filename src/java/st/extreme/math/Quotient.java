package st.extreme.math;

public class Quotient {

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
}