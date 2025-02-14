package ru.nsu.abramenko.prime.consistent;

import ru.nsu.abramenko.prime.AnalyseNumber;
import ru.nsu.abramenko.prime.FinderPrimeNum;

/** class for Finding non Prime consistent program.
 *
 */
public class ConsistentFind implements FinderPrimeNum {

    @Override
    public boolean containsPrimeNumber(int[] numbers) {
        for (int number : numbers) {
            if (!AnalyseNumber.getInstance().isPrime(number)) {
                return true;
            }
        }
        return false;
    }
}
