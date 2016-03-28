# fractions

An attempt to add, subtract, multiply and divide with exact precision in Java.

## Motivation

The handling of `BigDecimal` is a bit cumbersome. The programmer needs to specify the precision (or `MathContext`) for every arithmetic operation. My goal is to provide an easy to use class which frees the user of taking care about precision. The only point where the programmer has to make some assumptions about precision should be at the end of his calculations, when she needs to convert the final result back into a custom value.

## Idea

Every decimal value in the form `12345.6789` can be represented as a rational number, also called quotient or [fraction](https://en.wikipedia.org/wiki/Fraction_%28mathematics%29). In this example, it would be `123456789 / 10000`. 

Arithmetics with fractions can be reduced to *integer* multiplications, additions and subtractions. And those can be performed with **exact** precision.

