package ru.nsu.abramenko.pizzeria;

import ru.nsu.abramenko.Bqueue;

/** baker.
 *
 */
public class Baker extends Thread {
    private final int timeForOnePizza;
    private final Bqueue<Order> orders;
    private final Bqueue<Order> storage;

    /** constructor.
     *
     * @param timeForOnePizza time
     * @param orders orders
     * @param storage storage
     */
    public Baker(int timeForOnePizza, Bqueue<Order> orders, Bqueue<Order> storage) {
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
