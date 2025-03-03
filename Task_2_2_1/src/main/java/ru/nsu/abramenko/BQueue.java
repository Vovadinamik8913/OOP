package ru.nsu.abramenko;

import java.util.LinkedList;
import java.util.Queue;

/** own BlockedQueue.
 *
 * @param <T> Class
 */
public class BQueue<T> {
    private final Queue<T> data;
    private int maxSize;
    private final boolean isLimited;

    public BQueue(int maxSize) {
        this.data = new LinkedList<>();
        this.maxSize = maxSize;
        isLimited = true;
    }

    public BQueue() {
        this.data = new LinkedList<>();
        isLimited = false;
    }

    /** put in queue if possible.
     *
     * @param tmp data
     * @throws InterruptedException exception
     */
    public synchronized void add(T tmp) throws InterruptedException {
        while (isLimited && data.size() == maxSize) {
            wait();
        }
        data.add(tmp);
        notifyAll();
    }

    /** pop from queue.
     * if possible
     *
     * @return data
     * @throws InterruptedException exception
     */
    public synchronized T get() throws InterruptedException {
        while (data.isEmpty()) {
            wait();
        }
        T tmp = data.remove();
        notifyAll();
        return tmp;
    }

    /** current size of queue.
     *
     * @return size
     */
    public synchronized int size() {
        return data.size();
    }
}
