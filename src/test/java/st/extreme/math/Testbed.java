package st.extreme.math;

import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

public class Testbed {

	private static BigInteger[] BIG_INTEGERS = new BigInteger[10];
	static {
		BIG_INTEGERS[0] = new BigInteger("8482975720");
		BIG_INTEGERS[1] = new BigInteger("7279040277722");
		BIG_INTEGERS[2] = new BigInteger("-100288428930");
		BIG_INTEGERS[3] = new BigInteger("+5829988342");
		BIG_INTEGERS[4] = new BigInteger("5828910000222994");
		BIG_INTEGERS[5] = new BigInteger("-88279488292983");
		BIG_INTEGERS[6] = new BigInteger("9293");
		BIG_INTEGERS[7] = new BigInteger("882987997197577371738829238298392898989823");
		BIG_INTEGERS[8] = new BigInteger("11221298391898291");
		BIG_INTEGERS[9] = new BigInteger("-28928302939020039902009902993002984772777");

	}

	@Test
	@Ignore
	public void testBigIntegerMultiplication() {
		Random random = new Random(System.currentTimeMillis());
		// about 6.5 seconds on my macbook pro
		for (long l = 0; l < 99999999L; l++) {
			// pick two random big integers
			BigInteger bigInt1 = BIG_INTEGERS[random.nextInt(9)];
			BigInteger bigInt2 = BIG_INTEGERS[random.nextInt(9)];
			BigInteger bigInt3 = bigInt1.multiply(bigInt2);
			assertNotNull(bigInt3);
		}
	}

}
