package ru.nsu.abramenko.pizzeria;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.nsu.abramenko.Bqueue;
import ru.nsu.abramenko.PizzeriaConfig;

/** pizzeria.
 *
 */
public class Pizzeria {
    private final Bqueue<Order> storage;
    private final Bqueue<Order> orders;
    private final List<Baker> bakers;
    private final List<Deliveryman> deliverymen;

    /** constructor with args.
     *
     * @param n bakers
     * @param m deliverymen
     * @param t storageCapacity
     */
    public Pizzeria(int n, int m, int t) {
        storage = new Bqueue<>(t);
        bakers = new ArrayList<>();
        deliverymen = new ArrayList<>();
        orders = new Bqueue<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            bakers.add(
                    new Baker(
                    1000 * (random.nextInt(n) + 1), orders, storage)
            );
        }
        for (int i = 0; i < m; i++) {
            deliverymen.add(
                    new Deliveryman(
                    1000 * (random.nextInt(n) + 1), (random.nextInt(n) + 1), storage)
            );
        }
    }

    /** constructor from config.
     *
     * @throws IOException no such file config
     */
    public Pizzeria() throws IOException {
        PizzeriaConfig config = PizzeriaConfig.loadConfig();
        storage = new Bqueue<>(config.getStorageCapacity());
        orders = new Bqueue<>();
        bakers = new ArrayList<>();
        deliverymen = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < config.getBakers(); i++) {
            bakers.add(
                    new Baker(
                            1000 * (random.nextInt(config.getBakers()) + 1),
                            orders, storage)
            );
        }
        for (int i = 0; i < config.getDeliverymen(); i++) {
            deliverymen.add(
                    new Deliveryman(
                            1000 * (random.nextInt(config.getDeliverymen()) + 1),
                            (random.nextInt(config.getDeliverymen()) + 1), storage)
            );
        }
    }

    /** start work.
     *
     */
    public void work() {
        for (Baker baker : bakers) {
            baker.start();
        }
        for (Deliveryman deliveryman : deliverymen) {
            deliveryman.start();
        }
    }

    /** end work.
     *
     */
    public void close() {
        for (Baker baker : bakers) {
            baker.interrupt();
        }
        for (Deliveryman deliveryman : deliverymen) {
            deliveryman.interrupt();
        }
        for (Baker baker : bakers) {
            try {
                baker.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        for (Deliveryman deliveryman : deliverymen) {
            try {
                deliveryman.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /** number of Orders.
     *
     * @return size of orders
     */
    public int numberOfOrders() {
        return orders.size();
    }

    /** number of Baked.
     *
     * @return size of storage
     */
    public int numberOfBaked() {
        return storage.size();
    }


    /** add new order.
     *
     * @param order order
     * @throws InterruptedException exception
     */
    public void add(Order order) throws InterruptedException {
        orders.add(order);
    }

    /** main for tests.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Pizzeria pizzeria = null;
        try {
            pizzeria = new Pizzeria();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }
        pizzeria.work();
        try {
            for (int i = 0; i < 100; i++){
                Order order = new Order();
                pizzeria.add(order);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            pizzeria.close();
        }
    }
}
