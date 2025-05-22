package ru.nsu.abramenko;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Coordinator {
    private static final int COORDINATOR_PORT = 8080;
    private static final int DISCOVERY_PORT = 8082;
    private static final int WORKER_TIMEOUT = 5000;
    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
    private final ConcurrentHashMap<String, Long> workerAddresses = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void start() {
        new Thread(this::startDiscoveryService).start();
        startHttpServer();
        new Thread(this::checkWorkersHealth).start();
    }

    private void startDiscoveryService() {
        try (DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT)) {
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                if ("WORKER_REGISTER".equals(message)) {
                    String workerAddress = packet.getAddress().getHostAddress() + ":8081";
                    workerAddresses.put(workerAddress, System.currentTimeMillis());
                    System.out.println("Registered worker: " + workerAddress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
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

                boolean hasNonPrime = distributeTasks(numbers);

                Map<String, Boolean> response = new HashMap<>();
                response.put("hasNonPrime", hasNonPrime);
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

    private boolean distributeTasks(List<Long> numbers) {
        List<String> activeWorkers = new ArrayList<>(workerAddresses.keySet());
        if (activeWorkers.isEmpty()) {
            return false;
        }

        List<Future<Boolean>> futures = new ArrayList<>();
        int workerCount = activeWorkers.size();
        List<Long> shuffled = shuffle(numbers);

        for (int i = 0; i < workerCount; i++) {
            int from = i * shuffled.size() / workerCount;
            int to = (i + 1) * shuffled.size() / workerCount;
            List<Long> subList = shuffled.subList(from, to);

            String workerAddress = activeWorkers.get(i);
            futures.add(executor.submit(() -> sendTaskToWorker(workerAddress, subList)));
        }

        for (Future<Boolean> future : futures) {
            try {
                if (future.get(WORKER_TIMEOUT, TimeUnit.MILLISECONDS)) {
                    return true;
                }
            } catch (TimeoutException e) {
                System.err.println("Worker timeout, trying another worker...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

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

    private boolean sendTaskToWorker(String workerAddress, List<Long> numbers) {
        try {
            System.out.println(workerAddress);
            String host = workerAddress.split(":")[0];
            int port = Integer.parseInt(workerAddress.split(":")[1]);

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
        } catch (Exception e) {
            System.err.println("Error communicating with worker " + workerAddress + ": " + e.getMessage());
            return false;
        }
    }
}
