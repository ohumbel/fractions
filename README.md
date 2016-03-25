# quotient-math

A spike how I could add, subtract, multiply and divide with exact precision.

## Motivation

Every decimal value in the form `12345.6789` can be represented as a rational number, quotient or [fraction](https://en.wikipedia.org/wiki/Fraction_%28mathematics%29). In this example, it would be `123456789 / 10000`. 

Arithmetics with quotients can be reduced to *integer* multiplications, additions and subtractions. And those can be performed with **exact** precision.

## Still unclear
- what should the internal storage format for numerator and denominator be: `String` or `BigInteger` ?
- what is the fastest way to perform those integer arithmetics (note that we do not need division): `BigInteger` or a `String` (or `char[]`) based school math with multiplication tables ?
- what is the best way to turn the end result into a `BigDecimal` / `String` ?
