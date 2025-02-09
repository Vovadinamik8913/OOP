package ru.nsu.abramenko.prime;

public class AnalyseNumber {

    private static final int LEN = 1000000;
    private final boolean[] sieve;
    private static volatile AnalyseNumber instance;

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

    public static AnalyseNumber getInstance() {
        if (instance == null) {
            synchronized (AnalyseNumber.class) {
                if (instance == null) {
                    instance = new AnalyseNumber();
                }
            }
        }
        return instance;
    }
    
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
