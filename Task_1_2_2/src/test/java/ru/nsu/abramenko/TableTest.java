package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import ru.nsu.abramenko.hashtableown.HashTableOwn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TableTest {
    @Test
    void mainCheck() {
        Main.main(null);
        assertTrue(true);
    }

    @Test
    @DisplayName("DifferentSizeTest")
    void sizeCheck() {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        table.update("one", 1.0);
        table.put("two", 2);
        table.update("two", 2.0);

        HashTableOwn<String, Number> table1 = new HashTableOwn();
        table1.put("one", 1);
        table1.update("one", 1.0);
        table1.put("two", 2);
        table1.update("two", 2.0);
        table1.remove("two");
        assertTrue(!table1.equals(table));
    }

    @Test
    @DisplayName("ExceptionTest")
    void exceptionCheck() {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        table.update("one", 1.0);
        table.put("two", 2);
        table.update("two", 2.0);
        try {
            for (HashTableOwn.Node<String, Number> node : table) {
                table.remove("two");
            }
        } catch (ConcurrentModificationException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("ContainsTest")
    void containsTest() {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        table.update("one", 1.0);
        assertFalse(table.contains("one") &&  table.contains("two"));
    }

    @Test
    @DisplayName("GetTest")
    void getTest() {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        assertEquals(table.get("one").intValue(), 1);
    }

    @Test
    @DisplayName("RemoveTest")
    void removeTest() {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        table.remove("two");
        table.remove("one");
        assertEquals(table.contains("one"), false);
    }

    @Test
    @DisplayName("UpdateTest")
    void updateTest() {
        HashTableOwn<String, Number> table = new HashTableOwn();
        table.put("one", 1);
        table.update("one", 1.0);
        table.update("two", 2);
        assertTrue(table.contains("two"));
    }
}