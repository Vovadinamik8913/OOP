package ru.nsu.abramenko.pizzeria;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import ru.nsu.abramenko.BQueue;
import ru.nsu.abramenko.PizzeriaConfig;

/** pizzeria.
 *
 */
public class Pizzeria {
    private final BQueue<Order> storage;
    private final BQueue<Order> orders;
    private final List<Baker> bakers;
    private final List<Deliveryman> deliverymen;

    public Pizzeria(int n, int m, int t) {
        storage = new BQueue<>(t);
        bakers = new ArrayList<>();
        deliverymen = new ArrayList<>();
        orders = new BQueue<>();
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

    /** constructor.
     *
     * @throws IOException no such file config
     */
    public Pizzeria() throws IOException {
        PizzeriaConfig config = PizzeriaConfig.loadConfig();
        storage = new BQueue<>(config.getStorageCapacity());
        orders = new BQueue<>();
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
        Scanner scanner = new Scanner(System.in);
        pizzeria.work();
        try {
            String name = scanner.next();
            while (!name.equals("end")) {
                Order order = new Order(name);
                pizzeria.add(order);
                name = scanner.next();
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            pizzeria.close();
            scanner.close();
        }
    }
}
