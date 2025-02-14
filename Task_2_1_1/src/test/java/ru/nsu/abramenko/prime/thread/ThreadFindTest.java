package ru.nsu.abramenko.prime.thread;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThreadFindTest {
    @Test
    @DisplayName("trueTest")
    void basicTrueTest() {
        ThreadFind threadFind = new ThreadFind(8);
        int[] testArr = new int[]{6, 8, 7, 13, 5, 9, 4};
        assertTrue(threadFind.containsPrimeNumber(testArr));
        System.out.println("True Test: " + threadFind.timeOfFind(testArr));
    }

    @Test
    @DisplayName("falseTest")
    void basicFalseTest() {
        ThreadFind threadFind = new ThreadFind(12);
        int[] testArr = new int[]{
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053
        };
        assertFalse(threadFind.containsPrimeNumber(testArr));
        System.out.println("False Test: " + threadFind.timeOfFind(testArr));
    }

    @Test
    @DisplayName("largeNumTest")
    void bigNumTest() {
        ThreadFind threadFind = new ThreadFind(6);
        int[] testArr = new int[]{
            500000005, 100010352, 940501000, 500000000, 999999999, 1000000000
        };
        assertTrue(threadFind.containsPrimeNumber(testArr));
        System.out.println("Large Test: " + threadFind.timeOfFind(testArr));
    }

    @Test
    @DisplayName("largestTest")
    void largestTest() {
        ThreadFind threadFind = new ThreadFind(4);
        int size = 10000;
        int[] testArr = new int[size];
        Arrays.fill(testArr, 999999937);
        assertFalse(threadFind.containsPrimeNumber(testArr));
        System.out.println("LARGEST: " + threadFind.timeOfFind(testArr));
    }
}