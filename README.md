[![Build Status](https://travis-ci.org/ohumbel/fractions.svg)](https://travis-ci.org/ohumbel/fractions)
[![Code Coverage](https://img.shields.io/codecov/c/github/ohumbel/fractions/master.svg)](https://codecov.io/github/ohumbel/fractions?branch=master)
[![Apache License](https://img.shields.io/badge/license-Apache%202.0-orange.svg)](https://github.com/ohumbel/fractions/blob/master/LICENSE)
[![Supported Versions](https://img.shields.io/badge/Java-8%2C%209-blue.svg)](https://travis-ci.org/ohumbel/fractions)

![codecov.io](https://codecov.io/github/ohumbel/fractions/branch.svg?branch=master)

# fractions

Rational calculation: `add`, `subtract`, `multiply`, `divide` and `pow(int)` with exact precision in Java.


## Motivation

The handling of `BigDecimal` is a bit cumbersome. The programmer needs to specify the precision (or `MathContext`) for almost every division, and - depending on the call chain - for the other arithmetic operations as well.

My goal is to provide an easy to use class which frees the user of taking care about precision. 

The only point where the programmer has to make some assumptions about precision should be at the end of all the calculations, when she needs to convert the final result back into a custom value.


## Idea

Every decimal value in the form `12345.6789` can be represented as a rational number, also called [fraction](https://en.wikipedia.org/wiki/Fraction_%28mathematics%29). In this example, it would be `123456789 / 10000`. 

Arithmetics with fractions can be reduced to *integer* multiplications, *integer* additions and *integer* subtractions. And those can be performed with **exact** precision.


## Get it from maven central
```xml
<dependency>
  <groupId>st.extreme</groupId>
  <artifactId>fractions</artifactId>
  <version>1.1</version>
</dependency>
```
Please adapt to your build system of choice. For more information, see [Releases](https://github.com/ohumbel/fractions/releases).


## Implementation


Immutable arbitrary precision fractions.

The implementation is based on a `BigInteger` value for both numerator and denominator.
All operations on fractions can be performed through `BigInteger` multiplication, addition and subtraction. And these have exact precision.

Cancellation is done on construction, using `BigInteger.gcd(BigInteger)`.



### Use case 1: division and multiplication with the same value

This is the most common source of rounding problems.

```java
  @Test
  public void testUseCase1() {
    BigFraction start = BigFraction.valueOf("1000");
    BigFraction divisor = BigFraction.valueOf("21");
    BigFraction quotient = start.divide(divisor);
    BigFraction result = quotient.multiply(divisor);
    assertEquals("1000", result.toString());
    assertEquals("1000", result.toPlainString());
    assertEquals(start, result);
  }
 
  @Test
  public void testUseCase1_BigDecimal() {
    BigDecimal start = new BigDecimal("1000");
    BigDecimal divisor = new BigDecimal("21");
    BigDecimal quotient = start.divide(divisor, new MathContext(30, RoundingMode.HALF_UP));
    BigDecimal result = quotient.multiply(divisor);
    assertEquals("1000", result.toPlainString());
    assertEquals(start, result);
  }
```

While `testUseCase1()` passes, `testUseCase1_BigDecimal()` fails. It is very hard to find a `MathContext` so that the second test passes.


### Use case 2: calculation with fractions

```java
  @Test
  public void testUseCase2_multiply() {
    BigFraction bf1 = BigFraction.valueOf("2/3");
    BigFraction bf2 = BigFraction.valueOf("-6/7");
    BigFraction result = bf1.multiply(bf2);
    assertEquals("-4/7", result.toString());
  }

  @Test
  public void testUseCase2_divide() {
    BigFraction bf1 = BigFraction.valueOf("2/3");
    BigFraction bf2 = BigFraction.valueOf("6/7");
    BigFraction result = bf1.divide(bf2);
    assertEquals("7/9", result.toString());
  }

  @Test
  public void testUseCase2_add() {
    BigFraction bf1 = BigFraction.valueOf("2/15");
    BigFraction bf2 = BigFraction.valueOf("6/5");
    BigFraction result = bf1.add(bf2);
    assertEquals("4/3", result.toString());
  }

  @Test
  public void testUseCase2_subtract() {
    BigFraction bf1 = BigFraction.valueOf("8/15");
    BigFraction bf2 = BigFraction.valueOf("6/5");
    BigFraction result = bf1.subtract(bf2);
    assertEquals("-2/3", result.toString());
  }

  @Test
  public void testUseCase2_pow() {
    BigFraction bf1 = BigFraction.valueOf("-2/3");
    BigFraction result = bf1.pow(-3);
    assertEquals("-27/8", result.toString());
  }
```

## Release 1.0 - Changes since last release
- The implementation of `toString() ` and `toPlainString()` have switched. This reflects the benaviour of `BigDecimal.toString()` and `BigDecimal.toPlainString()`.
- The `.valueOf(String)` parsing of input can now handle more number-alike strings, especially `BigDecimal.toEngineeringString()` and `BigDecimal.toString()` with scientific notation.
- Numerator and denominator now keep their signs. This will be reverted with the next release.

## Release 1.1 - Changes since last release
- Running this library now requires Java 8.
- The `MANIFEST.MF` file now has an attribute `Automatic-Module-Name` with the value `st.extreme.math.fraction`, in order to ease the transition to Java 9.
- Due to the automatic module name, the package for `BigFraction` is now `st.extreme.math.fraction`, instead of `st.extreme.math` before.
- `equals()` is now properly implemented and verified by [EqualsVerifier](http://jqno.nl/equalsverifier/). As a consequence, the `BigFraction` class is now `final`.
- The `compareTo()` method now only accepts `BigFraction` any more (everything else would violate the `Comparable` contract). Thanks to cancellation, the natural ordering of `BigFraction` is now consistent with `equals()`.
- The new `compareToNumber()` method allows comparisons to `java.lang.Number` types.
- The `denominator` now is always kept positive (again). Some optimizations required this, so it is likely to stay this way.


## Contributors
The following people gave very valuable advice - many thanks to them:
- [1tchy](https://github.com/1tchy) suggested cancellation
- [reinhapa](https://github.com/reinhapa) helped with gradle and maven central
- [bk1](https://github.com/bk1) suggested using lcm, and other range optimizations
