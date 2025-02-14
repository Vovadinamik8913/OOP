package ru.nsu.abramenko.prime.stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class StreamFindTest {
    @Test
    @DisplayName("trueTest")
    void basicTrueTest() {
        StreamFind consistentFind = new StreamFind();
        int[] testArr = new int[]{6, 8, 7, 13, 5, 9, 4};
        assertTrue(consistentFind.containsPrimeNumber(testArr));
        System.out.println("True Test: " + consistentFind.timeOfFind(testArr));
    }

    @Test
    @DisplayName("falseTest")
    void basicFalseTest() {
        StreamFind consistentFind = new StreamFind();
        int[] testArr = new int[]{
            20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
            6998009, 6998029, 6998039, 20165149, 6998051, 6998053
        };
        assertFalse(consistentFind.containsPrimeNumber(testArr));
        System.out.println("False Test: " + consistentFind.timeOfFind(testArr));
    }

    @Test
    @DisplayName("largeNumTest")
    void bigNumTest() {
        StreamFind consistentFind = new StreamFind();
        int[] testArr = new int[]{
            500000005, 100010352, 940501000, 500000000, 999999999, 1000000000
        };
        assertTrue(consistentFind.containsPrimeNumber(testArr));
        System.out.println("Large Test: " + consistentFind.timeOfFind(testArr));
    }

    @Test
    @DisplayName("largestTest")
    void largestTest() {
        StreamFind consistentFind = new StreamFind();
        int size = 10000;
        int[] testArr = new int[size];
        Arrays.fill(testArr, 999999937);
        assertFalse(consistentFind.containsPrimeNumber(testArr));
        System.out.println("LARGEST: " + consistentFind.timeOfFind(testArr));
    }
}