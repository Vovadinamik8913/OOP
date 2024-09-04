package ru.nsu.abramenko;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {
    @Test
    void SampleTest(){
        assertArrayEquals(new int[]{1,2,3,4,5},Main.heapsort(new int[]{5,4,3,2,1}));
    }
    @Test
    void RandomTest(){
        int arr1[] = new int[]{1,4,22,1564,166,854,0};
        int arr2[]= new int[]{0,1,4,22,166,854,1564};
        assertArrayEquals(arr2,Main.heapsort(arr1));
    }
}