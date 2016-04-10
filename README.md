[![Build Status](https://travis-ci.org/ohumbel/fractions.svg)](https://travis-ci.org/ohumbel/fractions)
[![Apache License](https://img.shields.io/badge/license-Apache%202.0-orange.svg)](https://github.com/ohumbel/fractions/blob/master/LICENSE)
[![Supported Versions](https://img.shields.io/badge/Java-7%2C%208-blue.svg)](https://travis-ci.org/ohumbel/fractions)

# fractions

Rational calculation: an attempt to `add`, `subtract`, `multiply`, `divide` and `pow(int)` with exact precision in Java.


## Motivation

The handling of `BigDecimal` is a bit cumbersome. The programmer needs to specify the precision (or `MathContext`) for every arithmetic operation.

My goal is to provide an easy to use class which frees the user of taking care about precision. 

The only point where the programmer has to make some assumptions about precision should be at the end of all the calculations, when she needs to convert the final result back into a custom value.


## Idea

Every decimal value in the form `12345.6789` can be represented as a rational number, also called [fraction](https://en.wikipedia.org/wiki/Fraction_%28mathematics%29). In this example, it would be `123456789 / 10000`. 

Arithmetics with fractions can be reduced to *integer* multiplications, *integer* additions and *integer* subtractions. And those can be performed with **exact** precision.

