package ru.nsu.abramenko.hashtableown;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

/** My own hashtable.
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTableOwn<K, V> implements Iterable<HashTableOwn.Node<K, V>> {
    private static final double LADEN = 0.7;
    private Node<K, V>[] hashTable;
    private int capacity;
    private int size;

    /** constructor of empty table.
     *
     */
    public HashTableOwn() {
        this.hashTable = null;
        this.capacity = 0;
        this.size = 0;
    }

    /** resizing table and saving data.
     *
     */
    public void resize() {
        int newCapacity;
        if (capacity == 0) {
            newCapacity = 1;
        } else {
            newCapacity = capacity * 2;
        }
        Node<K, V>[] newTable = new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> current = hashTable[i];
            while (current != null) {
                Node<K, V> next = current.getNext();
                int newHash = Math.abs(current.getKey().hashCode()) % newCapacity;
                current.setNext(newTable[newHash]);
                newTable[newHash] = current;
                current = next;
            }
        }

        hashTable = newTable;
        capacity = newCapacity;
    }

    /** removing data by key.
     *
     * @param key key of node
     */
    public void remove(K key) {
        int hash = getIndex(key);
        Node<K, V> current = hashTable[hash];
        Node<K, V> prev = null;
        while (current != null) {
            if (key.equals(current.getKey())) {
                if (prev == null) {
                    hashTable[hash] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
                return;
            }
            prev = current;
            current = current.getNext();
        }
    }

    /** get if exists value of node by key.
     *
     * @param key key of node
     * @return value of node( or null)
     */
    public V get(K key) {
        int hash = getIndex(key);
        Node<K, V> current = hashTable[hash];
        Node<K, V> prev = null;
        while (current != null) {
            if (key.equals(current.getKey())) {
                return current.getValue();
            }
            prev = current;
            current = current.getNext();
        }
        return null;
    }

    /** get index in table by key.
     *
     * @param key key of node
     * @return index in table
     */
    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    /** isThere a node with that key.
     *
     * @param key key of node
     * @return true or false
     */
    public boolean contains(K key) {
        int hash = key.hashCode() % capacity;
        Node<K, V> current = hashTable[hash];
        Node<K, V> prev = null;
        while (current != null) {
            if (key.equals(current.getKey())) {
                return true;
            }
            prev = current;
            current = current.getNext();
        }
        return false;
    }

    /** put new value in table with key.
     * if exists do nothing
     *
     * @param key key of node
     * @param value value of node
     */
    public void put(K key, V value) {
        if (capacity == 0 || (double)size / capacity >= LADEN) {
            resize();
        }

        int hash = getIndex(key);
        Node<K, V> current = hashTable[hash];

        while (current != null) {
            if (key.equals(current.getKey())) {
                return;
            }
            current = current.getNext();
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode .setNext(hashTable[hash]);
        hashTable[hash] = newNode;
        size++;
    }

    /** updates old value of node to new.
     *  if isn`t exists put it
     *
     * @param key key of node
     * @param value value of node
     */
    public void update(K key, V value) {
        int hash = getIndex(key);
        Node<K, V> current = hashTable[hash];

        while (current != null) {
            if (key.equals(current.getKey())) {
                current.setValue(value);
                return;
            }
            current = current.getNext();
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.setNext(hashTable[hash]);
        hashTable[hash] = newNode;
        size++;
    }

    /** making table iterable.
     *
     * @return iterator
     */
    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {
            private final int objSize = size;
            private Node<K, V> obj = null;
            private int index = 0;

            /** isThere next node in table.
             *
             * @return true of false
             */
            @Override
            public boolean hasNext() {
                if (obj != null && obj.getNext() != null) {
                    return true;
                }
                while (index < hashTable.length) {
                    if (hashTable[index] != null) {
                        return true;
                    }
                    index++;
                }
                return false;
            }

            /** getting next node.
             *
             * @return next node
             */
            @Override
            public Node<K, V> next() {
                if (objSize != size) {
                    throw  new ConcurrentModificationException();
                }
                if (obj != null && obj.getNext() != null) {
                    obj = obj.next;
                } else {
                    obj = hashTable[index++];
                }
                return obj;
            }
        };
    }

    /** foreach with some action.
     *
     * @param action The action to be performed for each element
     */
    @Override
    public void forEach(Consumer<? super Node<K, V>> action) {
        Iterable.super.forEach(action);
    }


    /** returns table in string format.
     *
     * @return table to string
     */
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < capacity; i++) {
            if (hashTable[i] != null) {
                Node<K, V> cur = hashTable[i];
                while (cur != null) {
                    res += cur.toString() + " ";
                    cur = cur.getNext();
                }
                res += "\n";
            }
        }
        return res;
    }

    /** isThis obj equals.
     *
     * @param obj another table
     * @return true of false
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != HashTableOwn.class) {
            return false;
        }
        HashTableOwn<K, V> table = (HashTableOwn<K, V>) obj;
        if (size != table.size || capacity != table.capacity) {
            return false;
        }
        for (int i = 0; i < capacity; i++) {
            if (hashTable[i] == null && table.hashTable[i] == null) {
                continue;
            }
            if (hashTable[i] != null && table.hashTable[i] != null) {
                Node<K, V> curThis = hashTable[i];
                Node<K, V> curOther = table.hashTable[i];
                while (curThis != null) {
                    if (!curThis.equals(curOther)) {
                        return false;
                    }
                    curThis = curThis.next;
                    curOther = curOther.next;
                    if ((curThis == null && curOther != null)
                        || (curThis != null && curOther == null)) {
                        return false;
                    }
                }
            } else {
                return false;
            }

        }
        return true;
    }

    /** what the table is made of.
     *
     * @param <K> key
     * @param <V> value
     */
    public static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        /** constructor.
         *
         * @param key key
         * @param value value
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }


        /**set value.
         *
         * @param value newValue
         */
        public void setValue(V value) {
            this.value = value;
        }

        /** get next node.
         *
         * @return next node
         */
        public Node<K, V> getNext() {
            return next;
        }

        /** set next node.
         *
         * @param next next node
         */
        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        /** get key.
         *
         * @return key
         */
        public K getKey() {
            return key;
        }

        /** get value.
         *
         * @return value
         */
        public V getValue() {
            return value;
        }

        /** convert node to string.
         *
         * @return node to string
         */
        @Override
        public String toString() {
            return "(" + key.toString() + " => " + value.toString() + ")";
        }

        /** is obj equals.
         *
         * @param obj another node
         * @return true or false
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null || obj.getClass() == Node.class) {
                return false;
            }
            Node<K, V> node = (Node<K, V>) obj;
            return this.key.equals(node.key) && this.value.equals(node.value);
        }
    }
}
