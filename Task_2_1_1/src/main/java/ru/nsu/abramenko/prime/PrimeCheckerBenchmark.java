package ru.nsu.abramenko.prime;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import ru.nsu.abramenko.prime.consistent.ConsistentFind;
import ru.nsu.abramenko.prime.stream.StreamFind;
import ru.nsu.abramenko.prime.thread.ThreadFind;


/**
 * Class for Benchmark.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 3)
@Measurement(iterations = 20, time = 3)
@Fork(1)
public class PrimeCheckerBenchmark {
    int[] testArr;

    /**
     * Method to create array of primes.
     */
    @Setup
    public void setup() {
        testArr = new int[10000];
        Arrays.fill(testArr, 999999937);
    }

    /**
     * Test for nonParallel method.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testSequential(Blackhole blackhole) {
        ConsistentFind consistentFind = new ConsistentFind();
        boolean result = consistentFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 1 thread.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreadsOne(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(1);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 2 threads.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreadsTwo(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(2);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 8 threads.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreadsEight(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(8);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 4 threads.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreads4(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(4);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 16 thread.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreads16(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(16);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with parallelStream.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testParallelstream(Blackhole blackhole) {
        StreamFind streamFind = new StreamFind();
        boolean result = streamFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }
}
