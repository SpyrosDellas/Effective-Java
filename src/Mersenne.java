import java.math.BigInteger;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

/**
 * This class demonstrates usage of streams and invert mapping.
 *
 * @author Spyros Dellas
 */
public class Mersenne {

    /**
     * Produces an infinite stream of probabilistically guaranteed primes.
     * <p>
     * For the probabilistic guarantee offered see {@link BigInteger#nextProbablePrime() nextProbablePrime}.
     *
     * @return the stream of primes
     */
    private static Stream<BigInteger> primes() {
        return Stream.iterate(BigInteger.TWO, BigInteger::nextProbablePrime);
    }

    /**
     * Prints the first x Mersenne primes with a probabilistic guarantee {@code 1 - 1/2^100}.
     * <p>
     * Note: A Mersenne number is a number of the form 2^p − 1. If p is prime, the
     * corresponding Mersenne number may be prime; if so, it’s a Mersenne prime.
     *
     * @param x the number of primes to print
     */
    public static void printX(long x) {
        primes().map(prime -> (TWO.pow(prime.intValueExact()).subtract(ONE)))
                .filter(mersenne -> mersenne.isProbablePrime(100))
                .limit(x)
                .forEach(System.out::println);
    }

    /**
     * Prints the first x Mersenne primes with a probabilistic guarantee {@code 1 - 1/2^100},
     * together with their exponents.
     * <p>
     * The exponents are recovered via inverse mapping of the Mersenne prime to its bit length in
     * the forEach terminal operation.
     * <p>
     * Note: A Mersenne number is a number of the form 2^p − 1. If p is prime, the
     * corresponding Mersenne number may be prime; if so, it’s a Mersenne prime.
     *
     * @param x the number of primes to print
     */
    public static void printXWithExponent(long x) {
        primes().map(prime -> (TWO.pow(prime.intValueExact()).subtract(ONE)))
                .filter(mersenne -> mersenne.isProbablePrime(100))
                .limit(x)
                .forEach(mersenne -> System.out.println("2^" + mersenne.bitLength() + ": " + mersenne));
    }

    public static void main(String[] args) {
        printX(20);
        printXWithExponent(20);
    }

}
