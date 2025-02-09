package ru.nsu.abramenko.prime;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import ru.nsu.abramenko.prime.consistent.ConsistentFind;
import ru.nsu.abramenko.prime.stream.StreamFind;
import ru.nsu.abramenko.prime.thread.ThreadFind;


/**
 * Class for Benchmark.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class PrimeCheckerBenchmark {
    int[] testArr;

    /**
     * Method to create array of primes.
     */
    @Setup
    public void setup() {
        testArr = new int[1000];
        Arrays.fill(testArr, 999999937);
        AnalyseNumber.getInstance();
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
     * Test for parallel program with 50 threads.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreads50(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(50);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 64 thread.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreads64(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(64);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 256 threads.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreads256(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(256);
        boolean result = threadFind.containsPrimeNumber(testArr);
        blackhole.consume(result);
    }

    /**
     * Test for parallel program with 512 threads.
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testThreads512(Blackhole blackhole) {
        ThreadFind threadFind = new ThreadFind(512);
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
