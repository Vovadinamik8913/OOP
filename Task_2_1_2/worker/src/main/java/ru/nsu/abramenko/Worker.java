package ru.nsu.abramenko;

import java.io.*;
import java.net.*;
import java.util.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Worker {
    private static final int WORKER_PORT = 8081;
    private static final int DISCOVERY_PORT = 8082;
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
    private DatagramSocket registrationSocket;
    private volatile boolean isRunning = true;

    public void start() {
        try {
            registrationSocket = new DatagramSocket();
            new Thread(this::registerWithCoordinator).start();
            
            try (ServerSocket serverSocket = new ServerSocket(WORKER_PORT)) {
                System.out.println("Worker started on port " + WORKER_PORT);

                while (isRunning) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClientRequest(clientSocket)).start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (registrationSocket != null && !registrationSocket.isClosed()) {
                registrationSocket.close();
            }
        }
    }

    private void registerWithCoordinator() {
        try {
            InetAddress coordinatorAddress = InetAddress.getByName("coordinator");
            byte[] buffer = "WORKER_REGISTER".getBytes();
            DatagramPacket packet = new DatagramPacket(
                    buffer, buffer.length, coordinatorAddress, DISCOVERY_PORT);

            while (isRunning) {
                try {
                    registrationSocket.send(packet);
                    Thread.sleep(10000);
                } catch (IOException | InterruptedException e) {
                    if (!isRunning) {
                        break;
                    }
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String jsonInput = in.readLine();
            Map<String, Object> input = mapper.readValue(jsonInput, Map.class);
            List<Long> numbers = (List<Long>) input.get("numbers");

            boolean hasNonPrime = false;
            for (Long num : numbers) {
                if (!isPrime(num)) {
                    hasNonPrime = true;
                    break;
                }
            }
            System.out.println(hasNonPrime + " - result for: " + numbers);
            Map<String, Boolean> response = new HashMap<>();
            response.put("hasNonPrime", hasNonPrime);
            out.println(mapper.writeValueAsString(response));
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

    private boolean isPrime(long num) {
        if (num <= 1) return false;
        if (num <= 3) return true;
        if (num % 2 == 0) return false;

        for (long i = 5; i * i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}