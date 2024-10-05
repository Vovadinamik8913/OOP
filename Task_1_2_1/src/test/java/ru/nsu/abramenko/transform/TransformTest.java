package ru.nsu.abramenko.transform;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class TransformTest {
    @Test
    void interfaceTest() {
        Transform<Integer> transform1 = new IntegerTransform();
        int val = transform1.transform("100");
        int val2 = transform1.transform(null);
        Transform<String> transform2 = new StringTransform();
        String str = transform2.transform("100@");
        assertEquals(val + val2, 100);
        assertEquals(str, "100@");
    }
}