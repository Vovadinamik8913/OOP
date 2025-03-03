package ru.nsu.abramenko;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

class PizzeriaBenchmarkTest {
    @Test
    public void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(PizzeriaBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(options).run();
    }
}