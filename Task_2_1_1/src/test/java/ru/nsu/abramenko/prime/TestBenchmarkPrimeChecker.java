package ru.nsu.abramenko.prime;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

class TestBenchmarkPrimeChecker {

    @Test
    public void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(PrimeCheckerBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(options).run();
    }
}