package ru.nsu.abramenko.prime;

/** interface for finding non prime in sequences.
 *
 */
public interface FinderPrimeNum {
    boolean containsPrimeNumber(int[] numbers);

    /** time for check array.
     *
     * @param numbers numbers
     * @return time of checking
     */
    default double timeOfFind(int[] numbers) {
        long res = 0L;
        int tries = 100;
        for (int i = 0; i < tries; i++) {
            long start = System.nanoTime();
            containsPrimeNumber(numbers);
            long end = System.nanoTime();
            res += end - start;
        }

        return (double) res / tries / 100;
    }
}
