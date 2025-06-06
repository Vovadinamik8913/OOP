package ru.nsu.abramenko;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Coordinator class that manages worker nodes and distributes tasks.
 */
public class Coordinator {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
    private static final int COORDINATOR_PORT = 8080;
    private static final int DISCOVERY_PORT = 8082;
    private static final int WORKER_TIMEOUT = 5000;
    private static final int WORKING_POWER = 500;
    private static final int MAX_RETRY_TIME_MS = 5000;
    private static final int RETRY_INTERVAL_MS = 500;
    private static final int MIN_POWER = 1;
    private static final int MAX_POWER = 10;

    private final ConcurrentHashMap<String, Long> workerAddresses = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final int testCount = 1;
    private final AtomicLong totalExecutionTime = new AtomicLong(0);

    /**
     * Starts the coordinator service.
     */
    public void start() {
        new Thread(this::startDiscoveryService).start();
        startHttpServer();
        new Thread(this::checkWorkersHealth).start();
    }

    /**
     * Starts the discovery service for worker registration.
     */
    private void startDiscoveryService() {
        try (DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                if (message.contains("WORKER_REGISTER")) {
                    String workerAddress = packet.getAddress().getHostAddress();
                    workerAddresses.put(workerAddress, System.currentTimeMillis());
                    System.out.println("Registered worker: " + workerAddress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks the health of registered workers and removes unresponsive ones.
     */
    private void checkWorkersHealth() {
        while (true) {
            try {
                Thread.sleep(10000);

                long currentTime = System.currentTimeMillis();
                workerAddresses.entrySet().removeIf(entry ->
                        (currentTime - entry.getValue()) > 30000);

                System.out.println("Active workers: " + workerAddresses.keySet());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the HTTP server to handle client requests.
     */
    private void startHttpServer() {
        try (ServerSocket serverSocket = new ServerSocket(COORDINATOR_PORT)) {
            System.out.println("Coordinator started on port " + COORDINATOR_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(() -> handleClientRequest(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Distributes tasks among available workers.
     *
     * @param numbers      The list of numbers to be processed.
     * @param workingPower The initial working power.
     * @return True if a non-prime number is found, false otherwise.
     */
    private boolean distributeTasks(List<Long> numbers, int workingPower) {
        workingPower = Math.max(MIN_POWER, Math.min(workingPower, MAX_POWER)) * WORKING_POWER;

        List<String> activeWorkers = new ArrayList<>(workerAddresses.keySet());
        if (activeWorkers.isEmpty()) {
            return false;
        }

        List<Long> shuffled = shuffle(numbers);
        List<List<Long>> chunks = splitIntoChunks(shuffled, workingPower);
        Map<Integer, List<Long>> failedChunks = new ConcurrentHashMap<>();
        AtomicBoolean hasNonPrime = new AtomicBoolean(false);

        List<Future<?>> initialFutures = new ArrayList<>();
        for (int i = 0; i < chunks.size(); i++) {
            List<Long> chunk = chunks.get(i);
            final int chunkIndex = i;
            String workerAddress = activeWorkers.get(i % activeWorkers.size());

            initialFutures.add(executor.submit(() -> {
                try {
                    boolean result = sendTaskToWorkerWithRetry(workerAddress, chunk);
                    if (result) {
                        hasNonPrime.set(true);
                    }
                } catch (Exception e) {
                    failedChunks.put(chunkIndex, chunk);
                }
            }));
        }

        for (Future<?> future : initialFutures) {
            try {
                future.get(WORKER_TIMEOUT * 2, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (hasNonPrime.get()) {
            return true;
        }

        while (!failedChunks.isEmpty() && !hasNonPrime.get()) {
            List<Future<?>> retryFutures = new ArrayList<>();
            Iterator<Map.Entry<Integer, List<Long>>> iterator = failedChunks.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<Integer, List<Long>> entry = iterator.next();
                List<Long> chunk = entry.getValue();
                String workerAddress = activeWorkers.get(
                    ThreadLocalRandom.current().nextInt(activeWorkers.size()));

                retryFutures.add(executor.submit(() -> {
                    try {
                        boolean result = sendTaskToWorkerWithRetry(workerAddress, chunk);
                        if (result) {
                            hasNonPrime.set(true);
                        }
                        iterator.remove();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }));
            }

            for (Future<?> future : retryFutures) {
                try {
                    future.get(WORKER_TIMEOUT * 2, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (hasNonPrime.get()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Splits the list of numbers into smaller chunks based on the working power.
     *
     * @param numbers      The list of numbers to be split.
     * @param workingPower The working power determining the number of chunks.
     * @return A list of chunks, each containing a sublist of numbers.
     */
    private List<List<Long>> splitIntoChunks(List<Long> numbers, int workingPower) {
        List<List<Long>> chunks = new ArrayList<>();
        int chunkSize = Math.max(1, numbers.size() / workingPower);
        int remainder = numbers.size() % workingPower;
        int currentIndex = 0;

        for (int i = 0; i < workingPower; i++) {
            int thisChunkSize = chunkSize + (i < remainder ? 1 : 0);
            if (thisChunkSize == 0 || currentIndex >= numbers.size()) {
                break;
            }

            chunks.add(numbers.subList(currentIndex, currentIndex + thisChunkSize));
            currentIndex += thisChunkSize;
        }

        return chunks;
    }

    /**
     * Sends a task to a worker with retry mechanism in case of failure.
     *
     * @param workerAddress The address of the worker.
     * @param numbers       The list of numbers to be processed.
     * @return True if the task was successfully processed, false otherwise.
     */
    private boolean sendTaskToWorkerWithRetry(String workerAddress, List<Long> numbers) {
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < MAX_RETRY_TIME_MS) {
            try {
                return sendTaskToWorker(workerAddress, numbers);
            } catch (Exception e) {
                System.err.println("Error sending task to worker " + workerAddress + ": " + e.getMessage());
            }

            try {
                Thread.sleep(RETRY_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        return false;
    }

    /**
     * Handles incoming client requests.
     *
     * @param clientSocket The socket connected to the client.
     */
    private void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            StringBuilder request = new StringBuilder();
            String line;
            while (!(line = in.readLine()).isEmpty()) {
                request.append(line).append("\n");
            }

            if (request.toString().startsWith("POST")) {
                int contentLength = 0;
                for (String header : request.toString().split("\n")) {
                    if (header.toLowerCase().startsWith("content-length:")) {
                        contentLength = Integer.parseInt(header.substring(15).trim());
                    }
                }

                char[] body = new char[contentLength];
                in.read(body, 0, contentLength);
                String jsonInput = new String(body);

                Map<String, Object> input = mapper.readValue(jsonInput, Map.class);
                List<Long> numbers = (List<Long>) input.get("numbers");
                int workingPower = ((Number) input.get("workingPower")).intValue();
                boolean hasNonPrime = false;
                for (int z = 0; z < testCount; z++) {
                    long startTime = System.nanoTime();
                    hasNonPrime = distributeTasks(numbers, workingPower);
                    long end = System.nanoTime() - startTime;
                    totalExecutionTime.addAndGet(end);
                    System.out.println(end);
                }
                System.out.println("Average Time - " + totalExecutionTime.get() / testCount * 1.0);
                Map<String, Object> response = new HashMap<>();
                response.put("hasNonPrime", hasNonPrime);
                response.put("averageTime", totalExecutionTime.get() / testCount * 1.0);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: application/json");
                out.println("Connection: close");
                out.println();
                out.println(mapper.writeValueAsString(response));
            } else {
                out.println("HTTP/1.1 405 Method Not Allowed");
                out.println("Connection: close");
                out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shuffles the list of numbers randomly.
     *
     * @param numbers The list of numbers to be shuffled.
     * @return A new list containing the shuffled numbers.
     */
    private List<Long> shuffle(List<Long> numbers) {
        Random random = new Random();
        List<Long> res = new ArrayList<>(numbers);
        for (int i = 0; i < numbers.size(); i++) {
            int j = random.nextInt(numbers.size());
            long tmp = res.get(i);
            res.set(i, res.get(j));
            res.set(j, tmp);
        }
        return res;
    }

    /**
     * Sends a task to the specified worker.
     *
     * @param workerAddress The address of the worker.
     * @param numbers       The list of numbers to be processed.
     * @return True if the task was successfully processed, false otherwise.
     * @throws IOException If an I/O error occurs while communicating with the worker.
     */
    private boolean sendTaskToWorker(String workerAddress, List<Long> numbers) throws IOException {
        System.out.println(workerAddress);
        String[] part = workerAddress.split(":");
        int port = 8081;
        if (part.length > 1) {
            port = Integer.parseInt(workerAddress.split(":")[1]);
        }
        String host = workerAddress.split(":")[0];

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            Map<String, Object> task = new HashMap<>();
            task.put("numbers", numbers);
            out.println(mapper.writeValueAsString(task));
            String response = in.readLine();
            Map<String, Boolean> result = mapper.readValue(response, Map.class);
            return result.get("hasNonPrime");
        }
    }
}
