package ru.nsu.abramenko.pizzeria;

import ru.nsu.abramenko.BQueue;

public class Baker extends Thread {
    private final int timeForOnePizza;
    private final BQueue<Order> orders;
    private final BQueue<Order> storage;

    public Baker(int timeForOnePizza, BQueue<Order> orders, BQueue<Order> storage) {
        this.timeForOnePizza = timeForOnePizza;
        this.storage = storage;
        this.orders = orders;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Order order = orders.get();
                order.setStatus(Status.BAKING);
                System.out.println(order.getName() + " " + order.getStatus().getState());
                System.out.println();
                Thread.sleep(timeForOnePizza);
                order.setStatus(Status.BAKED);
                System.out.println(order.getName() + " " + order.getStatus().getState());
                storage.add(order);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
