package ru.nsu.abramenko;

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
import ru.nsu.abramenko.pizzeria.Order;
import ru.nsu.abramenko.pizzeria.Pizzeria;


/**
 * Class for Benchmark.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 3)
@Measurement(iterations = 20, time = 3)
@Fork(1)
public class PizzeriaBenchmark {
    Order[] testArr;
    int size = 100;

    /**
     * Method to create array of primes.
     */
    @Setup
    public void setup() {
        testArr = new Order[size];
        for (int i = 0; i < size; i++) {
            testArr[i] = new Order();
        }
    }

    /** Test for.
     * bakers much more than deliverymen
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testBakMoreDel(Blackhole blackhole) {
        Pizzeria pizzeria = new Pizzeria(10, 1, 1000);
        pizzeria.work();
        for (int i = 0; i < size; i++) {
            try {
                pizzeria.add(testArr[i]);
            } catch (InterruptedException e) {
                return;
            }
        }
        try {
            pizzeria.waitAllCompleted();
        } catch (InterruptedException e) {
            return;
        }
        pizzeria.close();
    }

    /** Test for.
     * bakers much less than deliverymen
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testBakLessDel(Blackhole blackhole) {
        Pizzeria pizzeria = new Pizzeria(1, 10, 1000);
        pizzeria.work();
        for (int i = 0; i < size; i++) {
            try {
                pizzeria.add(testArr[i]);
            } catch (InterruptedException e) {
                return;
            }
        }
        try {
            pizzeria.waitAllCompleted();
        } catch (InterruptedException e) {
            return;
        }
        pizzeria.close();
    }

    /** Test for.
     * bakers equals deliverymen
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testBakEqDel(Blackhole blackhole) {
        Pizzeria pizzeria = new Pizzeria(5, 5, 100);
        pizzeria.work();
        for (int i = 0; i < size; i++) {
            try {
                pizzeria.add(testArr[i]);
            } catch (InterruptedException e) {
                return;
            }
        }
        try {
            pizzeria.waitAllCompleted();
        } catch (InterruptedException e) {
            return;
        }
        pizzeria.close();
    }


    /** Test for.
     * small storage
     *
     * @param blackhole for optimisation.
     */
    @Benchmark
    public void testSmallStorage(Blackhole blackhole) {
        Pizzeria pizzeria = new Pizzeria(5, 5, 2);
        pizzeria.work();
        for (int i = 0; i < size; i++) {
            try {
                pizzeria.add(testArr[i]);
            } catch (InterruptedException e) {
                return;
            }
        }
        try {
            pizzeria.waitAllCompleted();
        } catch (InterruptedException e) {
            return;
        }
        pizzeria.close();
    }
}
