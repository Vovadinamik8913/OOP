package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class SubstringTest {
    private void createTestFile(String filePath, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        }
    }

    @Test
    @DisplayName("BaseTest")
    void baseTest() throws IOException {
        String filePath = "input.txt";
        String content = "абракабрадабра";
        String pattern = "бра";

        createTestFile(filePath, content);

        List<Integer> test = new ArrayList<>();
        test.add(1);
        test.add(6);
        test.add(11);
        List<Integer> res = Substring.find(filePath, pattern);
        assertEquals(res, test);

        new File(filePath).delete();

    }

    @Test
    @DisplayName("SubstringTests")
    public void testFindSubstringExists() throws IOException {
        String filePath = "testFile.txt";
        String content = "Hello, this is a test file. This file is for testing.";
        String pattern = "test";
        createTestFile(filePath, content);

        List<Integer> test = new ArrayList<>();
        test.add(17);
        test.add(45);
        List<Integer> res = Substring.find(filePath, pattern);
        assertEquals(res, test);

        new File(filePath).delete();
    }

    @Test
    @DisplayName("NotExists")
    public void testFindSubstringNotExists() throws IOException {
        String filePath = "testFile.txt";
        String content = "Hello, this is a test file. This file is for testing.";
        String pattern = "nonexistent";

        createTestFile(filePath, content);

        List<Integer> res = Substring.find(filePath, pattern);
        assertTrue(res.isEmpty());

        new File(filePath).delete();
    }

    @Test
    @DisplayName("Exception")
    public void errorTest() {
        assertThrows(RuntimeException.class,
                () -> {
                    Substring.find("fsdfs", "dsag");
        });
    }


    @Test
    @DisplayName("Big Test")
    public void bigTest() throws IOException {
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            content.append("a");
        }
        content.append("abc");
        for (int i = 0; i < 100000; i++) {
            content.append("a");
        }
        content.append("abc");
        String filePath = "testFile.txt";
        String pattern = "abc";
        createTestFile(filePath, content.toString());

        List<Integer> test = new ArrayList<>();
        test.add(100000);
        test.add(200003);
        List<Integer> res = Substring.find(filePath, pattern);
        assertEquals(res, test);

        new File(filePath).delete();
    }
}