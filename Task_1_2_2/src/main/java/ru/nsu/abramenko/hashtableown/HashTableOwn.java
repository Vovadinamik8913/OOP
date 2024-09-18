package ru.nsu.abramenko.hashtableown;


import java.util.*;
import java.util.function.Consumer;

public class HashTableOwn<K, V> implements Iterable<HashTableOwn.Node<K, V>> {
    private static final double LADEN = 0.7;
    private Node<K, V>[] hashTable;
    private int capacity;
    private int size;

    public HashTableOwn() {
        this.hashTable = null;
        this.capacity = 0;
        this.size = 0;
    }

    public void resize() {
        int new_capacity;
        if (capacity == 0) {
            new_capacity = 1;
        } else {
            new_capacity = capacity * 2;
        }
        Node<K, V>[] new_table = new Node[new_capacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> current = hashTable[i];
            while (current != null) {
                Node<K, V> next = current.getNext();
                int new_hash = Math.abs(current.getKey().hashCode()) % new_capacity;
                current.setNext(new_table[new_hash]);
                new_table[new_hash] = current;
                current = next;
            }
        }

        hashTable = new_table;
        capacity = new_capacity;
    }

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

    private int getIndex(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }

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

        Node<K, V> new_node = new Node<>(key, value);
        new_node.setNext(hashTable[hash]);
        hashTable[hash] = new_node;
        size++;
    }

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

        Node<K, V> new_node = new Node<>(key, value);
        new_node.setNext(hashTable[hash]);
        hashTable[hash] = new_node;
        size++;
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {
            private final int objSize = size;
            private Node<K, V> obj = null;
            private int index = 0;

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

            @Override
            public Node<K, V> next() {
                if (objSize != size) {
                    throw  new ConcurrentModificationException();
                }
                if (obj != null && obj.getNext() != null) {
                    obj = obj.next;
                } else {
                    while (index < hashTable.length && hashTable[index] == null) {
                        index++;
                    }
                    if (index >= hashTable.length) {
                        throw new NoSuchElementException();
                    }
                    obj = hashTable[index++];
                }
                return obj;
            }
        };
    }

    @Override
    public void forEach(Consumer<? super Node<K, V>> action) {
        Iterable.super.forEach(action);
    }


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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
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

    public static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            next = null;
        }


        public void setValue(V value) {
            this.value = value;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "(" + key.toString() + " => " + value.toString() + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            Node<K, V> node = (Node<K, V>) obj;
            return this.key.equals(node.key) && this.value.equals(node.value);
        }
    }
}
