package st.extreme.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Quotient implements Comparable<Quotient> {

  /**
   * The default {@link MathContext} for conversions into {@link BigDecimal}. Currently we use a precision of <code>500</code>.
   */
  private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(500, RoundingMode.HALF_UP);

  private static final Pattern DECIMAL_PART_PATTERN = Pattern.compile("([0-9]+)");

  private static final String ONE = "1";
  private static final String ZERO = "0";
  private static final String MINUS = "-";
  private static final String PLUS = "+";
  private static final String DOT = ".";

  private final BigInteger numerator;
  private final BigInteger denominator;

  private BigDecimal bigDecimalValue;

  /**
   * Create a quotient from two String values
   * 
   * @param numerator
   * @param denominator
   */
  Quotient(String numerator, String denominator) {
    this(new BigInteger(numerator), new BigInteger(denominator));
  }

  /**
   * Create a quotient from two BigInteger values
   * 
   * @param numerator
   * @param denominator
   */
  Quotient(BigInteger numerator, BigInteger denominator) {
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
   * Create a quotient with the reciprocal value
   * 
   * @return a new quotient with the reciprocal value of this quotient
   */
  public Quotient reciprocal() {
    return new Quotient(denominator, numerator);
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
   * @return a human readable representation
   */
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(numerator.toString());
    builder.append('/');
    builder.append(denominator.toString());
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
   *          in the format [sign][digits].[digits]
   * 
   * @return a quotient representing the passed in numeric value
   */
  public static Quotient valueOf(String numberString) {
    if (isZeroStringInput(numberString)) {
      return new Quotient(BigInteger.ZERO, BigInteger.ONE);
    }
    String[] values = numberString.split("\\.");
    String integerPart = values[0];
    if (values.length == 1) {
      if (isZeroStringInput(integerPart)) {
        return new Quotient(BigInteger.ZERO, BigInteger.ONE);
      } else {
        return new Quotient(new BigInteger(integerPart), BigInteger.ONE);
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
      denominator.append(ONE);
      int decimals = decimalPart.length();
      for (int i = 0; i < decimals; i++) {
        denominator.append(ZERO);
      }
      return new Quotient(numerator.toString(), denominator.toString());
    }
  }

  private static boolean isZeroStringInput(String numberString) {
    boolean isZero = false;
    if (numberString == null || numberString.isEmpty()) {
      isZero = true;
    } else {
      if (numberString.length() == 1) {
        switch (numberString) {
        case ZERO:
        case DOT:
        case MINUS:
        case PLUS:
          isZero = true;
        }
      }
    }
    return isZero;
  }

  /**
   * Convert to a {@link BigDecimal} value, using the {@link Quotient#DEFAULT_MATH_CONTEXT}.
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

  @Override
  public int compareTo(Quotient other) {
    if (denominator.equals(other.denominator)) {
      return numerator.compareTo(other.numerator);
    } else {
      return numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator));
    }
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
    // This fulfills the contract: equal fractions produce the same big decimal value.
    // It is allowed to produce the same hash code for non equal values (e.g. differing beyond the default math context precision).
    return bigDecimalValue().hashCode();
  }

  public Quotient multiply(Quotient value) {
    return new Quotient(numerator.multiply(value.numerator), denominator.multiply(value.denominator));
  }

  public Quotient divide(Quotient value) {
    if (BigInteger.ZERO.equals(value.numerator)) {
      throwDivisionByZero();
    }
    return multiply(value.reciprocal());
  }

  public Quotient add(Quotient value) {
    if (denominator.equals(value.denominator)) {
      return new Quotient(numerator.add(value.numerator), denominator);
    }
    return new Quotient(numerator.multiply(value.denominator).add(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  public Quotient subtract(Quotient value) {
    if (denominator.equals(value.denominator)) {
      return new Quotient(numerator.subtract(value.numerator), denominator);
    }
    return new Quotient(numerator.multiply(value.denominator).subtract(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  public Quotient negate() {
    return new Quotient(numerator.negate(), denominator);
  }

  public Quotient abs() {
    return new Quotient(numerator.abs(), denominator);
  }

  private static String buildNumberFormatExceptionMessage(String numberString) {
    return "illegal number format '".concat(numberString).concat("'.");
  }

  private void throwDivisionByZero() {
    throw new ArithmeticException("division by zero is not allowed.");
  }

}