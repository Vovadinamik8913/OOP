package ru.nsu.abramenko;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PrimeBenchmarkTest {

    private int sendMid(URL url, int[] numbers, int power) throws IOException {
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("numbers", numbers);
        requestData.put("workingPower", power);

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
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

    @Test
    public void runBenchmarks() throws Exception {
        int[] numbers = new int[10];
        Arrays.fill(numbers, 999999937);

        Coordinator coordinator = new Coordinator();
        new Thread(coordinator::start).start();
        Thread.sleep(2000);
        URL url = new URL("http://localhost:8080");

        Worker worker1 = new Worker("localhost");
        Worker worker2 = new Worker("localhost");

        new Thread(worker1::start).start();
        new Thread(worker2::start).start();

        sendMid(url, numbers, 1);
    }
}