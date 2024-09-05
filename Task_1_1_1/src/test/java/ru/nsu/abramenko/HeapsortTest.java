package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class HeapsortTest {
    @Test
    void checkMain() {
        Heapsort.main(null);
        assertTrue(true);
    }

    @Test
    @DisplayName("SampleArray")
    void sampleTest() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, Heapsort.heapsort(new int[] {5, 4, 3, 2, 1}));
    }

    @Test
    @DisplayName("BigSampleArray")
    void bigSampleTest() {
        int[] arr1 = new int[] {1, 4, 22, 1564, 166, 854, 0};
        int[] arr2 = new int[] {0, 1, 4, 22, 166, 854, 1564};
        assertArrayEquals(arr2, Heapsort.heapsort(arr1));
    }

    @Test
    @DisplayName("RandomArray")
    void randomTest() {
        int[] arr = new int[10000];
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt();
        }
        Heapsort.heapsort(arr);
        assertTrue(true);
    }
}