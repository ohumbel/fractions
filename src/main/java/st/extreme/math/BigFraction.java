package st.extreme.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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

  /** The pattern a decimal input String has to match */
  static final Pattern DECIMAL_PATTERN = Pattern.compile("0|([-|+]?(0\\.[0-9][0-9]*))|([-|+]?([1-9][0-9]*)(\\.[0-9])?[0-9]*)");

  /** The pattern a fraction input String has to match */
  static final Pattern FRACTION_PATTERN = Pattern.compile("(0|([-|+]?[1-9][0-9]*))/([-|+]?[1-9][0-9]*)");

  /**
   * The default {@link MathContext} for conversions into {@link BigDecimal}.<br>
   * Currently we use a precision of {@code 500}.
   */
  private static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(500, RoundingMode.HALF_UP);

  /** The serial version id */
  private static final long serialVersionUID = 1295910738820044783L;

  /** String constant for the digit {@code 1} */
  private static final String STRING_ONE = "1";

  /** String constant for the digit {@code 0} */
  private static final String STRING_ZERO = "0";

  private final BigInteger numerator;
  private final BigInteger denominator;

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
    if (object instanceof BigFraction) {
      return compareTo((BigFraction) object) == 0;
    } else if (object instanceof Number) {
      return compareTo(BigFraction.valueOf((Number) object)) == 0;
    } else {
      return false;
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
    return new BigFraction(BigInteger.valueOf(i), BigInteger.ONE);
  }

  /**
   * Create a new {@link BigFraction} from a {@code long} input
   * 
   * @param l
   *          a {@code long} value.
   * @return a {@code BigFraction} instance representing {@code l}.
   */
  public static BigFraction valueOf(long l) {
    return new BigFraction(BigInteger.valueOf(l), BigInteger.ONE);
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
    if (number instanceof Integer || number instanceof Long) {
      return new BigFraction(BigInteger.valueOf(number.longValue()), BigInteger.ONE);
    }
    return valueOf(number.toString());
  }

  /**
   * Create a new {@link BigFraction} from a {@link String} input.
   * <p>
   * The input can have the following formats:
   * <ul>
   * <li>{@code 123}
   * <li>{@code -123}
   * <li>{@code -123.678}
   * <li>{@code 2/3}
   * <li>{@code -2/3}
   * <li>{@code 2/-3}
   * <li>{@code -2/-3}
   * </ul>
   * 
   * @param numberString
   *          in one of the formats described above
   * 
   * @return The new {@link BigFraction} representing the passed in value
   * 
   * @throws NumberFormatException
   *           if the input does not represent a valid fraction
   */
  public static BigFraction valueOf(String numberString) {
    if (isZeroStringInput(numberString)) {
      return ZERO;
    }
    boolean matchesDecimal = DECIMAL_PATTERN.matcher(numberString).matches();
    final boolean matchesFraction;
    if (matchesDecimal) {
      matchesFraction = false;
    } else {
      matchesFraction = FRACTION_PATTERN.matcher(numberString).matches();
    }
    if (!matchesDecimal && !matchesFraction) {
      throw new NumberFormatException(buildNumberFormatExceptionMessage(numberString));
    }
    if (matchesDecimal) {
      return valueOfDecimalString(numberString);
    } else {
      return valueOfFractionString(numberString);
    }
  }

  /**
   * Convert to a {@link BigDecimal} value, using the {@link BigFraction#DEFAULT_MATH_CONTEXT}.
   * 
   * @return a maybe <strong>not exact</strong> representation of this {@link BigFraction} as a {@link BigDecimal} value.
   */
  public BigDecimal bigDecimalValue() {
    return bigDecimalValue(DEFAULT_MATH_CONTEXT);
  }

  /**
   * Convert to a {@link BigDecimal} value, using the given {@link MathContext}.
   * 
   * @param mathContext
   *          The desired target {@link MathContext}
   * 
   * @return a maybe <strong>not exact</strong> representation of this {@link BigFraction} as a {@link BigDecimal} value.
   */
  public BigDecimal bigDecimalValue(MathContext mathContext) {
    return new BigDecimal(numerator).divide(new BigDecimal(denominator), mathContext);
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
    if (numberString == null || numberString.isEmpty() || STRING_ZERO.equals(numberString)) {
      return true;
    }
    return false;
  }

  private void throwDivisionByZero() {
    throw new ArithmeticException("division by zero is not allowed.");
  }

  /**
   * @param decimalString
   *          The caller has to make sure that {@code decimalString} matches {@link DECIMAL_PATTERN}
   * @return the new {@link BigFraction} with the value of {@code decimalString}
   */
  private static BigFraction valueOfDecimalString(String decimalString) {
    String[] values = decimalString.split("\\.");
    String integerPart = values[0];
    if (values.length == 1) {
      if (isZeroStringInput(integerPart)) {
        return ZERO;
      } else {
        return new BigFraction(new BigInteger(integerPart), BigInteger.ONE);
      }
    } else {
      String decimalPart = values[1];
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
   * @param fractionString
   *          The caller has to make sure that {@code fractionString} matches {@link FRACTION_PATTERN}
   * @return the new {@link BigFraction} with the value of {@code fractionString}
   */
  private static BigFraction valueOfFractionString(String fractionString) {
    String[] values = fractionString.split("/");
    return new BigFraction(values[0], values[1]);
  }

}