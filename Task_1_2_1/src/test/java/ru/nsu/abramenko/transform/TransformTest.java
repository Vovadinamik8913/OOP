package ru.nsu.abramenko.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class TransformTest {
    @Test
    void interfaceTest() {
        Transform<Integer> transform1 = new IntegerTransform();
        int val = transform1.transform("100");
        Transform<String> transform2 = new StringTransform();
        String str = transform2.transform("100@");
        assertEquals(val, 100);
        assertEquals(str, "100@");
    }
}