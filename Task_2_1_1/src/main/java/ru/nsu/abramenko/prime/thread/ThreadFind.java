package ru.nsu.abramenko.prime.thread;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.abramenko.prime.AnalyseNumber;
import ru.nsu.abramenko.prime.FinderPrimeNum;

/** class for Finding non Prime by threads.
 *
 */
public class ThreadFind implements FinderPrimeNum {

    private final int numThreads;
    private final AtomicBoolean isFound;

    /** constructor.
     *
     * @param numThreads number of threads
     * @throws RuntimeException if numthreads > 1000
     */
    public ThreadFind(int numThreads) throws RuntimeException {
        this.numThreads = numThreads;
        if (numThreads > 1000) {
            throw new RuntimeException("Too much threads");
        }
        isFound = new AtomicBoolean(false);
    }

    private int[] shuffle(int[] numbers) {
        int[] res = numbers.clone();
        Random random = new Random();
        for (int i = 0; i < res.length; i++) {
            int ind = random.nextInt(res.length);
            int tmp = res[i];
            res[i] = res[ind];
            res[ind] = tmp;
        }
        return res;
    }

    @Override
    public boolean containsPrimeNumber(int[] numbers) throws RuntimeException {
        int[] arr = shuffle(numbers);
        int len = Math.min(arr.length, numThreads);
        Thread[] threads = new Thread[len];
        for (int i = 0; i < len && !isFound.get(); i++) {
            int index = i;
            threads[i] = new Thread(() -> {
                for (int j = index; j < arr.length && !isFound.get(); j += len) {
                    if (!AnalyseNumber.getInstance().isPrime(arr[j])) {
                        isFound.set(true);
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                if (isFound.get()) {
                    return isFound.get();
                }
                if (thread != null) {
                    thread.join();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return isFound.get();
    }
}
