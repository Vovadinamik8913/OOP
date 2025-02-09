package ru.nsu.abramenko.prime.stream;

import java.util.Arrays;
import ru.nsu.abramenko.prime.AnalyseNumber;
import ru.nsu.abramenko.prime.FinderPrimeNum;

/** class for Finding non Prime by streams.
 *
 */
public class StreamFind implements FinderPrimeNum {

    @Override
    public boolean containsPrimeNumber(int[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(num -> !AnalyseNumber.getInstance().isPrime(num));
    }
}
