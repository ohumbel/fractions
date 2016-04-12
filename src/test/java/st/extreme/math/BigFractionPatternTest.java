package st.extreme.math;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BigFractionPatternTest {

  @Test
  public void testFractionPattern() {
    assertTrue(matchesFractionPattern("2/3"));
    assertTrue(matchesFractionPattern("-2/3"));
    assertTrue(matchesFractionPattern("2/-3"));
    assertTrue(matchesFractionPattern("-2/-3"));
    assertTrue(matchesFractionPattern("+2/3"));
    assertTrue(matchesFractionPattern("2/+3"));
    assertTrue(matchesFractionPattern("+2/+3"));
  }

  @Test
  public void testFractionPattern_IllegalInput() {
    assertFalse(matchesFractionPattern("2.3"));
    assertFalse(matchesFractionPattern("2/"));
    assertFalse(matchesFractionPattern("/3"));
    assertFalse(matchesFractionPattern("2"));
    assertFalse(matchesFractionPattern("2-"));
    assertFalse(matchesFractionPattern("01"));
    assertFalse(matchesFractionPattern("-01"));
    assertFalse(matchesFractionPattern("+01"));
    assertFalse(matchesFractionPattern("01/02"));
    assertFalse(matchesFractionPattern("-01/+02"));
    assertFalse(matchesFractionPattern("+01/-02"));
  }

  @Test
  public void testDecimalPattern() {
    assertTrue(matchesDecimalPattern("120"));
    assertTrue(matchesDecimalPattern("12"));
    assertTrue(matchesDecimalPattern("0"));
    assertTrue(matchesDecimalPattern("0.1"));
    assertTrue(matchesDecimalPattern("0.0"));
    assertTrue(matchesDecimalPattern("0.01"));
    assertTrue(matchesDecimalPattern("0.000"));
    assertTrue(matchesDecimalPattern("123.4"));
    assertTrue(matchesDecimalPattern("123.456"));
    assertTrue(matchesDecimalPattern("1.0"));
    assertTrue(matchesDecimalPattern("1.000"));
    assertTrue(matchesDecimalPattern("123.000"));
  }

  @Test
  public void testDecimalPattern_Minus() {
    assertTrue(matchesDecimalPattern("-120"));
    assertTrue(matchesDecimalPattern("-12"));
    assertTrue(matchesDecimalPattern("-0.1"));
    assertTrue(matchesDecimalPattern("-0.0"));
    assertTrue(matchesDecimalPattern("-0.01"));
    assertTrue(matchesDecimalPattern("-123.4"));
    assertTrue(matchesDecimalPattern("-123.456"));
    assertTrue(matchesDecimalPattern("-1.0"));
    assertTrue(matchesDecimalPattern("-1.000"));
    assertTrue(matchesDecimalPattern("-123.000"));
  }

  @Test
  public void testDecimalPattern_Plus() {
    assertTrue(matchesDecimalPattern("+120"));
    assertTrue(matchesDecimalPattern("+12"));
    assertTrue(matchesDecimalPattern("+0.1"));
    assertTrue(matchesDecimalPattern("+0.0"));
    assertTrue(matchesDecimalPattern("+0.01"));
    assertTrue(matchesDecimalPattern("+123.4"));
    assertTrue(matchesDecimalPattern("+123.456"));
    assertTrue(matchesDecimalPattern("+1.0"));
    assertTrue(matchesDecimalPattern("+1.000"));
    assertTrue(matchesDecimalPattern("+123.000"));
  }

  @Test
  public void testDecimalPattern_Questionable() {
    assertTrue(matchesDecimalPattern("+0.0"));
    assertTrue(matchesDecimalPattern("+0.000"));
    assertTrue(matchesDecimalPattern("-0.0"));
    assertTrue(matchesDecimalPattern("-0.000"));
  }

  @Test
  public void testDecimalPattern_IllegalInput() {
    assertFalse(matchesDecimalPattern("01"));
    assertFalse(matchesDecimalPattern("1x0"));
    assertFalse(matchesDecimalPattern("0x4"));
    assertFalse(matchesDecimalPattern("120."));
    assertFalse(matchesDecimalPattern(".01"));
    assertFalse(matchesDecimalPattern("+0"));
    assertFalse(matchesDecimalPattern("-0"));
  }

  private boolean matchesFractionPattern(String input) {
    return BigFraction.FRACTION_PATTERN.matcher(input).matches();
  }

  private boolean matchesDecimalPattern(String input) {
    return BigFraction.DECIMAL_PATTERN.matcher(input).matches();
  }

}
