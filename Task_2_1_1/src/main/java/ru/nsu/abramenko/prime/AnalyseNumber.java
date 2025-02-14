package ru.nsu.abramenko.prime;

import lombok.Getter;

/** analysing number is prime or not.
 *
 */
public class AnalyseNumber {

    private static final int LEN = 1000000;
    private final boolean[] sieve;
    @Getter
    private static AnalyseNumber instance = new AnalyseNumber();
    private AnalyseNumber() {
        sieve = new boolean[LEN];
        sieve[0] = true;
        sieve[1] = true;
        for (int i = 2; i < LEN; i++) {
            if (!sieve[i]) {
                for (int j = i + i; j < LEN; j += i) {
                    sieve[j] = true;
                }
            }
        }
    }


    /** isPrime?.
     *
     * @param number number
     * @return true or false
     */
    public boolean isPrime(int number) {
        if (number < LEN) {
            return !sieve[number];
        }
        if (number % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
