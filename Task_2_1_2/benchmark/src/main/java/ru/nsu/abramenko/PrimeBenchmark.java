package ru.nsu.abramenko;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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


/**
 * Class for Benchmark.
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 3)
@Measurement(iterations = 20, time = 3)
@Fork(1)
public class PrimeBenchmark {
    long[] mid;
    Coordinator coordinator;
    URL url;
    ObjectMapper objectMapper;

    /**
     * Method to create array of primes.
     */
    @Setup
    public void setup() throws InterruptedException, MalformedURLException {
        mid = new long[1000];
        Arrays.fill(mid, 999999937L);

        coordinator = new Coordinator();
        new Thread(() -> coordinator.start()).start();
        Thread.sleep(2000);
        url = new URL("http://localhost:8080");

        objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
    }

    private int sendMid(int power) throws IOException {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("numbers", mid);
        requestData.put("workingPower", power);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInput = objectMapper.writeValueAsString(requestData);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInput.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        return connection.getResponseCode();
    }

    @Benchmark
    public void twoWorkersMid(Blackhole blackhole) throws IOException {
        Worker worker1 = new Worker("localhost");
        Worker worker2 = new Worker("localhost");

        new Thread(worker1::start).start();
        new Thread(worker2::start).start();

        int responseCode = sendMid(1);
        blackhole.consume(responseCode);
    }

    @Benchmark
    public void fourWorkersMid(Blackhole blackhole) throws IOException {
        Worker worker1 = new Worker("localhost");
        Worker worker2 = new Worker("localhost");
        Worker worker3 = new Worker("localhost");
        Worker worker4 = new Worker("localhost");

        new Thread(worker1::start).start();
        new Thread(worker2::start).start();
        new Thread(worker3::start).start();
        new Thread(worker4::start).start();

        int responseCode = sendMid(1);
        blackhole.consume(responseCode);
    }


    @Benchmark
    public void eightWorkersMid(Blackhole blackhole) throws IOException {
        Worker worker1 = new Worker("localhost");
        Worker worker2 = new Worker("localhost");
        Worker worker3 = new Worker("localhost");
        Worker worker4 = new Worker("localhost");

        Worker worker5 = new Worker("localhost");
        Worker worker6 = new Worker("localhost");
        Worker worker7 = new Worker("localhost");
        Worker worker8 = new Worker("localhost");

        new Thread(worker1::start).start();
        new Thread(worker2::start).start();
        new Thread(worker3::start).start();
        new Thread(worker4::start).start();

        new Thread(worker5::start).start();
        new Thread(worker6::start).start();
        new Thread(worker7::start).start();
        new Thread(worker8::start).start();


        int responseCode = sendMid(1);
        blackhole.consume(responseCode);
    }
}