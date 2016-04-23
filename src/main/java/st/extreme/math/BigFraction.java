package st.extreme.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Immutable arbitrary precision fractions.
 * <p>
 * The implementation is based on a {@link BigInteger} value for both numerator and denominator.<br>
 * All operations on fractions can be performed through {@link BigInteger} multiplication, addition and subtraction. And these have
 * <strong>exact</strong> precision.
 * <p>
 * Cancellation is done on construction, using {@link BigInteger#gcd(BigInteger)}.
 * <p>
 * <strong>Use case 1: division and multiplication with the same value</strong><br>
 * This is the most common source of rounding problems.
 * 
 * <pre>
 * &#64;Test
 * public void testUseCase1() {
 *   BigFraction start = BigFraction.valueOf("1000");
 *   BigFraction divisor = BigFraction.valueOf("21");
 *   BigFraction quotient = start.divide(divisor);
 *   BigFraction result = quotient.multiply(divisor);
 *   assertEquals("1000", result.toString());
 *   assertEquals("1000", result.toFractionString());
 *   assertEquals(start, result);
 * }
 * 
 * &#64;Test
 * public void testUseCase1_BigDecimal() {
 *   BigDecimal start = new BigDecimal("1000");
 *   BigDecimal divisor = new BigDecimal("21");
 *   BigDecimal quotient = start.divide(divisor, new MathContext(30, RoundingMode.HALF_UP));
 *   BigDecimal result = quotient.multiply(divisor);
 *   assertEquals("1000", result.toPlainString());
 *   assertEquals(start, result);
 * }
 * </pre>
 * 
 * While {@code testUseCase1()} passes, {@code testUseCase1_BigDecimal()} fails. It is very hard to find a {@link MathContext} so that the
 * second test passes.
 * <p>
 * <strong>Use case 2: calculation with fractions</strong>
 * 
 * <pre>
 * &#64;Test
 * public void testUseCase2_multiply() {
 *   BigFraction bf1 = BigFraction.valueOf("2/3");
 *   BigFraction bf2 = BigFraction.valueOf("-6/7");
 *   BigFraction result = bf1.multiply(bf2);
 *   assertEquals("-4/7", result.toFractionString());
 * }
 * 
 * &#64;Test
 * public void testUseCase2_divide() {
 *   BigFraction bf1 = BigFraction.valueOf("2/3");
 *   BigFraction bf2 = BigFraction.valueOf("6/7");
 *   BigFraction result = bf1.divide(bf2);
 *   assertEquals("7/9", result.toFractionString());
 * }
 * 
 * &#64;Test
 * public void testUseCase2_add() {
 *   BigFraction bf1 = BigFraction.valueOf("2/15");
 *   BigFraction bf2 = BigFraction.valueOf("6/5");
 *   BigFraction result = bf1.add(bf2);
 *   assertEquals("4/3", result.toFractionString());
 * }
 * 
 * &#64;Test
 * public void testUseCase2_subtract() {
 *   BigFraction bf1 = BigFraction.valueOf("8/15");
 *   BigFraction bf2 = BigFraction.valueOf("6/5");
 *   BigFraction result = bf1.subtract(bf2);
 *   assertEquals("-2/3", result.toFractionString());
 * }
 * 
 * &#64;Test
 * public void testUseCase2_pow() {
 *   BigFraction bf1 = BigFraction.valueOf("-2/3");
 *   BigFraction result = bf1.pow(-3);
 *   assertEquals("-27/8", result.toFractionString());
 * }
 * </pre>
 * 
 * @author Otmar Humbel
 */
public class BigFraction extends Number implements Comparable<Number> {

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
   * The default {@link MathContext} for conversions into {@link BigDecimal}.
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
   *          The numerator
   * @param denominator
   *          The denominator
   */
  public BigFraction(String numerator, String denominator) {
    this(new BigInteger(numerator), new BigInteger(denominator));
  }

  /**
   * Create a {@link BigFraction} from two {@link BigInteger} values.
   * 
   * @param numerator
   *          The numerator
   * @param denominator
   *          The denominator
   */
  public BigFraction(BigInteger numerator, BigInteger denominator) {
    if (BigInteger.ZERO.equals(denominator)) {
      throw new ArithmeticException("division by zero is not allowed.");
    }
    // because of "cross multiplying" in compareTo(), always keep the denominator positive
    if (denominator.signum() < 0) {
      numerator = numerator.negate();
      denominator = denominator.negate();
    }
    // cancel if possible
    BigInteger gcd = numerator.gcd(denominator);
    if (gcd.compareTo(BigInteger.ONE) > 0) {
      numerator = numerator.divide(gcd);
      denominator = denominator.divide(gcd);
    }
    this.numerator = numerator;
    this.denominator = denominator;
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

  /**
   * Compares this {@code BigFraction} with the specified {@link Number}.
   *
   * @param number
   *          {@link Number} to which this BigFraction is to be compared.
   * @return {@code -1}, {@code 0} or {@code 1} as this BigFraction is numerically less than, equal to, or greater than {@code number}.
   */
  @Override
  public int compareTo(Number number) {
    final BigFraction other;
    if (number instanceof BigFraction) {
      other = (BigFraction) number;
    } else {
      other = BigFraction.valueOf(number);
    }
    if (denominator.equals(other.denominator)) {
      return numerator.compareTo(other.numerator);
    } else {
      return numerator.multiply(other.denominator).compareTo(other.numerator.multiply(denominator));
    }
  }

  /**
   * Compares this {@code BigFraction} with the specified {@link Object} for equality.<br>
   * Equality can only be reached by {@code object} being another {@link BigFraction}.
   * <p>
   * To determine if this {@code BigFraction} is numerically equal to a {@link Number}, use {@link #compareTo(Number)}.
   * 
   * @param object
   *          {@link Object} to which this {@code BigFraction} is to be compared.
   * 
   * @return {@code true} if and only if the specified Object is a {@link BigFraction} whose value is numerically equal to this BigFraction.
   * 
   * @see BigFraction#compareTo(Number)
   */
  @Override
  public boolean equals(Object object) {
    if (object instanceof BigFraction) {
      return compareTo((BigFraction) object) == 0;
    } else {
      return false;
    }
  }

  /**
   * Returns the hash code for this BigFraction.
   *
   * @return hash code for this BigFraction.
   */
  @Override
  public int hashCode() {
    int hash = 17;
    hash = 31 * hash + denominator.hashCode();
    hash = 31 * hash + numerator.hashCode();
    return hash;
  }

  /**
   * Create a {@link BigFraction} with the reciprocal value of this BigFraction.
   * 
   * @return a new {@link BigFraction} with the reciprocal value of this BigFraction.
   */
  public BigFraction reciprocal() {
    return new BigFraction(denominator, numerator);
  }

  /**
   * Returns the numerator of this BigFraction.
   * 
   * @return the numerator
   */
  public BigInteger getNumerator() {
    return numerator;
  }

  /**
   * Returns the denominator of this BigFraction.
   * 
   * @return the denominator
   */
  public BigInteger getDenominator() {
    return denominator;
  }

  /**
   * Returns the {@code signum} function of this BigFraction.
   *
   * @return {@code -1}, {@code 0} or {@code 1} as the value of this BigFraction is negative, zero or positive.
   */
  public int signum() {
    return numerator.signum();
  }

  /**
   * Returns a human readable fractional representation of this BigFraction, such as {@code -2/3}.<br>
   * This representation is always exactly parseable by {@link #valueOf(String)}.
   * 
   * @return a fractional representation of this BigFraction.
   */
  public String toFractionString() {
    StringBuilder builder = new StringBuilder();
    builder.append(numerator.toString());
    if (BigInteger.ONE.compareTo(denominator) != 0) {
      builder.append('/');
      builder.append(denominator.toString());
    }
    return builder.toString();
  }

  /**
   * Returns a human readable numerical representation of this BigFraction, such as {@code -1.75}.<br>
   * This representation might not always be exactly parseable by {@link #valueOf(String)}.
   * 
   * @see #bigDecimalValue()
   * @see BigDecimal#toPlainString()
   * 
   * @return a numerical representation of this {@link BigFraction}.
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
   * Create a new {@link BigFraction} from a {@link Number} input.
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
   * @return a new {@code BigFraction} representing the passed in {@code numberString}.
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
   * Convert this {@code BigFraction } into a {@link BigDecimal} value, using the {@link BigFraction#DEFAULT_MATH_CONTEXT}.<br>
   * The default {@link MathContext} uses precision {@code 500} and {@link RoundingMode#HALF_UP}
   * 
   * @return a maybe <strong>not exact</strong> representation of this {@code BigFraction} as a {@link BigDecimal} value.
   */
  public BigDecimal bigDecimalValue() {
    return bigDecimalValue(DEFAULT_MATH_CONTEXT);
  }

  /**
   * Convert this {@code BigFraction } into a {@link BigDecimal} value, using the given {@link MathContext}.
   * 
   * @param mathContext
   *          The desired target {@link MathContext}
   * 
   * @return a maybe <strong>not exact</strong> representation of this {@code BigFraction} as a {@link BigDecimal} value.
   */
  public BigDecimal bigDecimalValue(MathContext mathContext) {
    return new BigDecimal(numerator).divide(new BigDecimal(denominator), mathContext);
  }

  /**
   * Multiply this {@code BigFraction} by another {@link BigFraction} value.
   * 
   * @param value
   *          The value this {@code BigFraction} is to be multiplied with.
   * @return a new {@code BigFraction} representing the product of this {@code BigFraction} and {@code value}.
   */
  public BigFraction multiply(BigFraction value) {
    boolean cancelUpperLeftLowerRight = false;
    boolean cancelLowerLeftUpperRight = false;
    if (numerator.equals(value.denominator)) {
      cancelUpperLeftLowerRight = true;
    }
    if (denominator.equals(value.numerator)) {
      cancelLowerLeftUpperRight = true;
    }
    if (cancelUpperLeftLowerRight && cancelLowerLeftUpperRight) {
      return ONE;
    } else if (cancelUpperLeftLowerRight) {
      return new BigFraction(value.numerator, denominator);
    } else if (cancelLowerLeftUpperRight) {
      return new BigFraction(numerator, value.denominator);
    } else {
      return new BigFraction(numerator.multiply(value.numerator), denominator.multiply(value.denominator));
    }
  }

  /**
   * Divide this {@code BigFraction} by another {@link BigFraction} value.
   * 
   * @param value
   *          The value this {@code BigFraction} is to be divided with.
   * @return a new {@code BigFraction} representing the quotient of this {@code BigFraction} and {@code value}.
   */
  public BigFraction divide(BigFraction value) {
    return multiply(value.reciprocal());
  }

  /**
   * Add this {@code BigFraction} and another {@link BigFraction} value.
   * 
   * @param value
   *          The value this {@code BigFraction} is to be added to.
   * @return a new {@code BigFraction} representing sum of this {@code BigFraction} and {@code value}.
   */
  public BigFraction add(BigFraction value) {
    if (denominator.equals(value.denominator)) {
      return new BigFraction(numerator.add(value.numerator), denominator);
    }
    return new BigFraction(numerator.multiply(value.denominator).add(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  /**
   * Subtract another {@link BigFraction} value from this {@code BigFraction}.
   * 
   * @param value
   *          The value to be subtracted from this {@code BigFraction}.
   * @return a new {@code BigFraction} representing this {@code BigFraction} minus {@code value}.
   */
  public BigFraction subtract(BigFraction value) {
    if (denominator.equals(value.denominator)) {
      return new BigFraction(numerator.subtract(value.numerator), denominator);
    }
    return new BigFraction(numerator.multiply(value.denominator).subtract(value.numerator.multiply(denominator)),
        denominator.multiply(value.denominator));
  }

  /**
   * Negate this {@code BigFraction}.
   * 
   * @return a new {@code BigFraction} representing the product of this {@code BigFraction} and {@code -1}.
   */
  public BigFraction negate() {
    return new BigFraction(numerator.negate(), denominator);
  }

  /**
   * Returns the absolute value of this {@code BigFraction}.
   * 
   * @return a new {@code BigFraction} representing the absolute value of this {@code BigFraction}.
   */
  public BigFraction abs() {
    return new BigFraction(numerator.abs(), denominator);
  }

  /**
   * Returns the power of this {@code BigFraction} by an {@code int} exponent.
   * 
   * @param exponent
   *          The exponent. Can be any {@code int}, including negative values.
   * @return a new {@code BigFraction} representing {@code this}<sup>{@code exponent}</sup>.
   */
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

  /**
   * Build the message for a {@link NumberFormatException}.
   * 
   * @param numberString
   * @return the message
   */
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

  /**
   * @param decimalString
   *          The caller has to make sure that {@code decimalString} matches {@link DECIMAL_PATTERN}
   * @return a new {@link BigFraction} with the value of {@code decimalString}
   */
  private static BigFraction valueOfDecimalString(String decimalString) {
    String[] values = decimalString.split("\\.");
    String integerPart = values[0];
    if (values.length == 1) {
      return new BigFraction(new BigInteger(integerPart), BigInteger.ONE);
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
   * @return a new {@link BigFraction} with the value of {@code fractionString}
   */
  private static BigFraction valueOfFractionString(String fractionString) {
    String[] values = fractionString.split("/");
    return new BigFraction(values[0], values[1]);
  }

}