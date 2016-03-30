package st.extreme.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fraction extends Number implements Comparable<Fraction> {

  /**
   * The fraction representing the value <code>1</code>
   */
  public static final Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);

  /**
   * The fraction representing the value <code>0</code>
   */
  public static final Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);

  /**
   * The default {@link MathContext} for conversions into {@link BigDecimal}. Currently we use a precision of <code>500</code>.
   */
  private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(500, RoundingMode.HALF_UP);

  private static final long serialVersionUID = 1295910738820044783L;
  private static final Pattern DECIMAL_PART_PATTERN = Pattern.compile("([0-9]+)");
  private static final String STRING_ONE = "1";
  private static final String STRING_ZERO = "0";
  private static final String STRING_MINUS = "-";
  private static final String STRING_PLUS = "+";
  private static final String STRING_DOT = ".";

  private final BigInteger numerator;
  private final BigInteger denominator;
  private BigDecimal bigDecimalValue;

  /**
   * Create a fraction from two {@link String} values.
   * <p>
   * Both values should be accepted by {@link BigInteger#BigInteger(String)}.
   * 
   * @param numerator
   * @param denominator
   */
  public Fraction(String numerator, String denominator) {
    this(new BigInteger(numerator), new BigInteger(denominator));
  }

  /**
   * Create a fraction from two {@link BigInteger} values.
   * 
   * @param numerator
   * @param denominator
   */
  public Fraction(BigInteger numerator, BigInteger denominator) {
    if (BigInteger.ZERO.equals(denominator)) {
      throwDivisionByZero();
    }
    if (denominator.signum() < 0) {
      // because of "cross multiplying" in compareTo(), always keep the denominator positive
      this.numerator = numerator.negate();
      this.denominator = denominator.negate();

    } else {
      this.numerator = numerator;
      this.denominator = denominator;
    }
  }

  /**
   * Converts this fraction into an <code>int</code> value.
   * 
   * @see {@link BigDecimal#intValue()}
   */
  @Override
  public int intValue() {
    return bigDecimalValue().intValue();
  }

  /**
   * Converts this fraction into a <code>long</code> value.
   * 
   * @see {@link BigDecimal#longValue()}
   */
  @Override
  public long longValue() {
    return bigDecimalValue().longValue();
  }

  /**
   * Converts this fraction into a <code>float</code> value.
   * 
   * @see {@link BigDecimal#floatValue()}
   */
  @Override
  public float floatValue() {
    return bigDecimalValue().floatValue();
  }

  /**
   * Converts this fraction into a <code>double</code> value.
   * 
   * @see {@link BigDecimal#doubleValue()}
   */
  @Override
  public double doubleValue() {
    return bigDecimalValue().doubleValue();
  }

  @Override
  public int compareTo(Fraction other) {
    if (denominator.equals(other.denominator)) {
      return numerator.compareTo(other.numerator);
    } else {
      return numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator));
    }
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Fraction)) {
      return false;
    } else {
      return compareTo((Fraction) object) == 0;
    }
  }

  @Override
  public int hashCode() {
    // This fulfills the contract: equal fractions produce the same big decimal value.
    // It is allowed to produce the same hash code for non equal values (e.g. differing beyond the default math context precision).
    return bigDecimalValue().hashCode();
  }

  /**
   * Create a fraction with the reciprocal value
   * 
   * @return a new fraction with the reciprocal value of this fraction
   */
  public Fraction reciprocal() {
    return new Fraction(denominator, numerator);
  }

  public BigInteger getNumerator() {
    return numerator;
  }

  public BigInteger getDenominator() {
    return denominator;
  }

  public boolean isPositive() {
    int numeratorSignum = numerator.signum();
    // let zero be positive
    return numeratorSignum == denominator.signum() || numeratorSignum == 0;
  }

  /**
   * A fraction representation, such as <code>-2/3</code>.
   * 
   * @return a human readable representation of this fraction
   */
  public String toFractionString() {
    StringBuilder builder = new StringBuilder();
    builder.append(numerator.toString());
    builder.append('/');
    builder.append(denominator.toString());
    return builder.toString();
  }

  /**
   * A numerical representation of this fraction, such as -1.75, like in {@link BigDecimal#toPlainString()}.
   * 
   * @return a numerical representation of this fraction
   */
  public String toString() {
    return bigDecimalValue().toPlainString();
  }

  /**
   * Create a new fraction from an int input
   */
  public static Fraction valueOf(int i) {
    return valueOf(Integer.valueOf(i));
  }

  /**
   * Create a new fraction from a long input
   */
  public static Fraction valueOf(long l) {
    return valueOf(Long.valueOf(l));
  }

  /**
   * Create a new fraction from a double input
   */
  public static Fraction valueOf(double d) {
    return valueOf(Double.valueOf(d));
  }

  /**
   * Create a new fraction from a float input
   */
  public static Fraction valueOf(float f) {
    return valueOf(Float.valueOf(f));
  }

  /**
   * Create a new fraction from {@link Number} input
   */
  public static Fraction valueOf(Number number) {
    return valueOf(number.toString());
  }

  /**
   * Create a new fraction from {@link BigDecimal} input
   */
  public static Fraction valueOf(BigDecimal bigDecimal) {
    return valueOf(bigDecimal.toPlainString());
  }

  /**
   * Create a new fraction from a {@link String} input
   * 
   * @param numberString
   *          in the format [sign][digits].[digits]
   * 
   * @return a fraction representing the passed in numeric value
   */
  public static Fraction valueOf(String numberString) {
    if (isZeroStringInput(numberString)) {
      return ZERO;
    }
    String[] values = numberString.split("\\.");
    String integerPart = values[0];
    if (values.length == 1) {
      if (isZeroStringInput(integerPart)) {
        return ZERO;
      } else {
        return new Fraction(new BigInteger(integerPart), BigInteger.ONE);
      }
    } else {
      String decimalPart = values[1];
      Matcher decimalPartMatcher = DECIMAL_PART_PATTERN.matcher(decimalPart);
      if (!decimalPartMatcher.matches()) {
        throw new NumberFormatException(buildNumberFormatExceptionMessage(decimalPart));
      }
      StringBuilder numerator = new StringBuilder(integerPart);
      numerator.append(decimalPart);
      StringBuilder denominator = new StringBuilder();
      denominator.append(STRING_ONE);
      int decimals = decimalPart.length();
      for (int i = 0; i < decimals; i++) {
        denominator.append(STRING_ZERO);
      }
      return new Fraction(numerator.toString(), denominator.toString());
    }
  }

  private static boolean isZeroStringInput(String numberString) {
    boolean isZero = false;
    if (numberString == null || numberString.isEmpty()) {
      isZero = true;
    } else {
      if (numberString.length() == 1) {
        switch (numberString) {
        case STRING_ZERO:
        case STRING_DOT:
        case STRING_MINUS:
        case STRING_PLUS:
          isZero = true;
        }
      }
    }
    return isZero;
  }

  /**
   * Convert to a {@link BigDecimal} value, using the {@link Fraction#DEFAULT_MATH_CONTEXT}.
   * 
   * @return a <strong>not exact</strong> representation of this fraction as a {@link BigDecimal} value.
   */
  public BigDecimal bigDecimalValue() {
    // because of immutability, we can lazily evaluate the big decimal value
    if (bigDecimalValue == null) {
      bigDecimalValue = new BigDecimal(numerator).divide(new BigDecimal(denominator), DEFAULT_MATH_CONTEXT);
    }
    return bigDecimalValue;
  }

  public Fraction multiply(Fraction value) {
    return new Fraction(numerator.multiply(value.numerator), denominator.multiply(value.denominator));
  }

  public Fraction divide(Fraction value) {
    return multiply(value.reciprocal());
  }

  public Fraction add(Fraction value) {
    if (denominator.equals(value.denominator)) {
      return new Fraction(numerator.add(value.numerator), denominator);
    }
    return new Fraction(numerator.multiply(value.denominator).add(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  public Fraction subtract(Fraction value) {
    if (denominator.equals(value.denominator)) {
      return new Fraction(numerator.subtract(value.numerator), denominator);
    }
    return new Fraction(numerator.multiply(value.denominator).subtract(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  public Fraction negate() {
    return new Fraction(numerator.negate(), denominator);
  }

  public Fraction abs() {
    return new Fraction(numerator.abs(), denominator);
  }

  public Fraction pow(int exponent) {
    if (exponent == 0) {
      return ONE;
    }
    if (BigInteger.ZERO.equals(numerator)) {
      return ZERO;
    }
    if (exponent < 0) {
      return reciprocal().pow(-exponent);
    }
    return new Fraction(numerator.pow(exponent), denominator.pow(exponent));
  }

  private static String buildNumberFormatExceptionMessage(String numberString) {
    return "illegal number format '".concat(numberString).concat("'.");
  }

  private void throwDivisionByZero() {
    throw new ArithmeticException("division by zero is not allowed.");
  }
}