package ru.nsu.abramenko.pizzeria;

import java.util.LinkedList;
import java.util.Queue;
import ru.nsu.abramenko.BQueue;

public class Deliveryman extends Thread {
    private final int maxPlace;
    private final int speed;
    private final Queue<Order> orders;
    private final BQueue<Order> storage;

    public Deliveryman(int speed, int maxPlace, BQueue<Order> storage) {
        this.speed = speed;
        this.storage = storage;
        this.maxPlace = maxPlace;
        orders = new LinkedList<>();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                do {
                    Order order = storage.get();
                    order.setStatus(Status.DELIVERING);
                    orders.add(order);
                } while (!Thread.currentThread().isInterrupted()
                        && orders.size() != maxPlace && storage.size() != 0);
                if (!Thread.currentThread().isInterrupted()) {
                    for (Order order : orders) {
                        order.setStatus(Status.DELIVERING);
                        System.out.println(order.getName() + " " + order.getStatus().getState());
                    }
                    Thread.sleep((long) speed * orders.size());
                    for (Order order : orders) {
                        order.setStatus(Status.DONE);
                        System.out.println(order.getName() + " " + order.getStatus().getState());
                    }
                    orders.clear();
                } else {
                    for (Order order : orders) {
                        storage.add(order);
                    }
                    orders.clear();
                }

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
