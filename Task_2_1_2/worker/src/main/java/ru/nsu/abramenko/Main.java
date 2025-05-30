package ru.nsu.abramenko;

/**
 * Main class for starting the worker.
 */
public class Main {
    /**
     * Main method for starting the worker.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Worker worker = new Worker("coordinator");
        worker.start();
    }
}