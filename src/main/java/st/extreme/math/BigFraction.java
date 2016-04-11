package st.extreme.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigFraction extends Number implements Comparable<BigFraction> {

  /**
   * The {@link BigFraction} representing the value {@code 1}
   */
  public static final BigFraction ONE = new BigFraction(BigInteger.ONE, BigInteger.ONE);

  /**
   * The {@link BigFraction} representing the value {@code 0}
   */
  public static final BigFraction ZERO = new BigFraction(BigInteger.ZERO, BigInteger.ONE);

  /**
   * The default {@link MathContext} for conversions into {@link BigDecimal}. Currently we use a precision of {@code 500}.
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
   * Create a {@link BigFraction} from two {@link String} values.
   * <p>
   * Both values have to be accepted by {@link BigInteger#BigInteger(String)}.
   * 
   * @param numerator
   *          numerator
   * @param denominator
   *          denominator
   */
  public BigFraction(String numerator, String denominator) {
    this(new BigInteger(numerator), new BigInteger(denominator));
  }

  /**
   * Create a {@link BigFraction} from two {@link BigInteger} values.
   * 
   * @param numerator
   *          numerator
   * @param denominator
   *          denominator
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
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
   * Converts this {@link BigFraction} into an {@code int} value.
   * 
   * @see BigDecimal#intValue()
   */
  @Override
  public int intValue() {
    return bigDecimalValue().intValue();
  }

  /**
   * Converts this {@link BigFraction} into a {@code long} value.
   * 
   * @see BigDecimal#longValue()
   */
  @Override
  public long longValue() {
    return bigDecimalValue().longValue();
  }

  /**
   * Converts this {@link BigFraction} into a {@code float} value.
   * 
   * @see BigDecimal#floatValue()
   */
  @Override
  public float floatValue() {
    return bigDecimalValue().floatValue();
  }

  /**
   * Converts this {@link BigFraction} into a {@code double} value.
   * 
   * @see BigDecimal#doubleValue()
   */
  @Override
  public double doubleValue() {
    return bigDecimalValue().doubleValue();
  }

  @Override
  public int compareTo(BigFraction other) {
    if (denominator.equals(other.denominator)) {
      return numerator.compareTo(other.numerator);
    } else {
      return numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator));
    }
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof BigFraction)) {
      // should be comparable to another Number
      return false;
    } else {
      return compareTo((BigFraction) object) == 0;
    }
  }

  @Override
  public int hashCode() {
    // This fulfills the contract: equal BigFractions produce the same big decimal value.
    // It is allowed to produce the same hash code for non equal values (e.g. differing beyond the default math context precision).
    return bigDecimalValue().hashCode();
  }

  /**
   * Create a {@link BigFraction} with the reciprocal value.
   * 
   * @return a new {@link BigFraction} with the reciprocal value of this {@link BigFraction}
   */
  public BigFraction reciprocal() {
    return new BigFraction(denominator, numerator);
  }

  public BigInteger getNumerator() {
    return numerator;
  }

  public BigInteger getDenominator() {
    return denominator;
  }

  /**
   * Returns the signum function of this {@link BigFraction}.
   *
   * @return {@code -1}, {@code 0} or {@code 1} as the value of this {@link BigFraction} is negative, zero or positive.
   */
  public int signum() {
    return numerator.signum();
  }

  /**
   * A {@link BigFraction} representation, such as {@code -2/3}.
   * 
   * @return a human readable representation of this {@link BigFraction}
   */
  public String toFractionString() {
    StringBuilder builder = new StringBuilder();
    builder.append(numerator.toString());
    builder.append('/');
    builder.append(denominator.toString());
    return builder.toString();
  }

  /**
   * A numerical representation of this {@link BigFraction}, such as -1.75, like in {@link BigDecimal#toPlainString()}.
   * 
   * @return a numerical representation of this {@link BigFraction}
   */
  public String toString() {
    return bigDecimalValue().toPlainString();
  }

  /**
   * Create a new {@link BigFraction} from an {@code int} input
   * 
   * @param i
   *          an {@code int} value.
   * @return a {@code BigFraction} instance representing {@code i}.
   */
  public static BigFraction valueOf(int i) {
    return valueOf(Integer.valueOf(i));
  }

  /**
   * Create a new {@link BigFraction} from a {@code long} input
   * 
   * @param l
   *          a {@code long} value.
   * @return a {@code BigFraction} instance representing {@code l}.
   */
  public static BigFraction valueOf(long l) {
    return valueOf(Long.valueOf(l));
  }

  /**
   * Create a new {@link BigFraction} from a {@code double} input
   * 
   * @param d
   *          a {@code double} value.
   * @return a {@code BigFraction} instance representing {@code d}.
   */
  public static BigFraction valueOf(double d) {
    return valueOf(Double.valueOf(d));
  }

  /**
   * Create a new {@link BigFraction} from a {@code float} input
   * 
   * @param f
   *          a {@code float} value.
   * @return a {@code BigFraction} instance representing {@code f}.
   */
  public static BigFraction valueOf(float f) {
    return valueOf(Float.valueOf(f));
  }

  /**
   * Create a new {@link BigFraction} from {@link Number} input
   * 
   * @param number
   *          a {@link Number} value.
   * @return a {@code BigFraction} instance representing {@code number}.
   */
  public static BigFraction valueOf(Number number) {
    if (number instanceof BigDecimal) {
      return valueOf(((BigDecimal) number).toPlainString());
    }
    return valueOf(number.toString());
  }

  /**
   * Create a new {@link BigFraction} from a {@link String} input
   * 
   * @param numberString
   *          in the format [sign][digits].[digits]
   * 
   * @return a {@link BigFraction} representing the passed in numeric value
   */
  public static BigFraction valueOf(String numberString) {
    if (isZeroStringInput(numberString)) {
      return ZERO;
    }
    String[] values = numberString.split("\\.");
    String integerPart = values[0];
    if (values.length == 1) {
      if (isZeroStringInput(integerPart)) {
        return ZERO;
      } else {
        return new BigFraction(new BigInteger(integerPart), BigInteger.ONE);
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
      return new BigFraction(numerator.toString(), denominator.toString());
    }
  }

  /**
   * Convert to a {@link BigDecimal} value, using the {@link BigFraction#DEFAULT_MATH_CONTEXT}.
   * 
   * @return a <strong>not exact</strong> representation of this {@link BigFraction} as a {@link BigDecimal} value.
   */
  public BigDecimal bigDecimalValue() {
    // because of immutability, we can lazily evaluate the big decimal value
    if (bigDecimalValue == null) {
      bigDecimalValue = new BigDecimal(numerator).divide(new BigDecimal(denominator), DEFAULT_MATH_CONTEXT);
    }
    return bigDecimalValue;
  }

  public BigFraction multiply(BigFraction value) {
    return new BigFraction(numerator.multiply(value.numerator), denominator.multiply(value.denominator));
  }

  public BigFraction divide(BigFraction value) {
    return multiply(value.reciprocal());
  }

  public BigFraction add(BigFraction value) {
    if (denominator.equals(value.denominator)) {
      return new BigFraction(numerator.add(value.numerator), denominator);
    }
    return new BigFraction(numerator.multiply(value.denominator).add(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  public BigFraction subtract(BigFraction value) {
    if (denominator.equals(value.denominator)) {
      return new BigFraction(numerator.subtract(value.numerator), denominator);
    }
    return new BigFraction(numerator.multiply(value.denominator).subtract(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  public BigFraction negate() {
    return new BigFraction(numerator.negate(), denominator);
  }

  public BigFraction abs() {
    return new BigFraction(numerator.abs(), denominator);
  }

  public BigFraction pow(int exponent) {
    if (exponent == 0) {
      return ONE;
    }
    if (BigInteger.ZERO.equals(numerator)) {
      return ZERO;
    }
    if (exponent < 0) {
      return reciprocal().pow(-exponent);
    }
    return new BigFraction(numerator.pow(exponent), denominator.pow(exponent));
  }

  private static String buildNumberFormatExceptionMessage(String numberString) {
    return "illegal number format '".concat(numberString).concat("'.");
  }

  /**
   * Determine if the input qualifies as {@link BigFraction#ZERO}.
   * 
   * @param numberString
   * @return {@code true} if the string is evaluated to zero, {@code false} otherwise.
   */
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

  private void throwDivisionByZero() {
    throw new ArithmeticException("division by zero is not allowed.");
  }
}