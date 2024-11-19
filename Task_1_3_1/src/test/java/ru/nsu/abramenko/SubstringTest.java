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

        createTestFile(filePath, content);

        List<Long> test = new ArrayList<>();
        test.add(1L);
        test.add(6L);
        test.add(11L);
        List<Long> res = Substring.find(filePath, "бра");
        assertEquals(res, test);

        new File(filePath).delete();

    }

    @Test
    @DisplayName("SubstringTests")
    public void testFindSubstringExists() throws IOException {
        String filePath = "testFile.txt";
        String content = "Hello, this is a test file. This file is for testing.";
        createTestFile(filePath, content);

        List<Long> test = new ArrayList<>();
        test.add(17L);
        test.add(45L);
        List<Long> res = Substring.find(filePath, "test");
        assertEquals(res, test);

        new File(filePath).delete();
    }

    @Test
    @DisplayName("NotExists")
    public void testFindSubstringNotExists() throws IOException {
        String filePath = "testFile.txt";
        String content = "Hello, this is a test file. This file is for testing.";

        createTestFile(filePath, content);

        List<Long> res = Substring.find(filePath, "none");
        assertTrue(res.isEmpty());

        new File(filePath).delete();
    }

    @Test
    @DisplayName("Exception")
    public void errorTest() {
        assertThrows(IOException.class,
                () -> {
                    Substring.find("fsdfs", "dsag");
                });
    }


    @Test
    @DisplayName("Big Test")
    public void bigTest() throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("a".repeat(100000));
        content.append("abc");
        content.append("a".repeat(100000));
        content.append("abc");
        String filePath = "testFile.txt";
        createTestFile(filePath, content.toString());

        List<Long> test = new ArrayList<>();
        test.add(100000L);
        test.add(200003L);
        List<Long> res = Substring.find(filePath, "abc");
        assertEquals(res, test);

        new File(filePath).delete();
    }

    @Test
    @DisplayName("Big Rus Test")
    public void bigRusTest() throws IOException {
        String content = "абв"
                + "а".repeat(100000)
                + "абв"
                + "а".repeat(100000)
                + "абв";
        String filePath = "testFile.txt";
        createTestFile(filePath, content);

        List<Long> test = new ArrayList<>();
        test.add(0L);
        test.add(100003L);
        test.add(200006L);
        List<Long> res = Substring.find(filePath, "абв");
        assertEquals(res, test);

        new File(filePath).delete();
    }

    @Test
    @DisplayName("Book Test")
    public void bookTest() throws IOException {
        List<Long> res = Substring.find("test3.txt", "было");
        assertEquals(182, res.size());
    }

    @Test
    @DisplayName("Biggest Test")
    public void biggestTest() throws IOException {
        String filePath = "testFile.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            for (int i = 0; i < 100; i++) {
                writer.write("abc".repeat(1000000) + "абв");
            }
        }
        List<Long> res = Substring.find(filePath, "абв");
        assertEquals(res.size(), 2500);
        new File(filePath).delete();
    }

    @Test
    @DisplayName("Chinese Test")
    public void chineseTest() throws IOException {
        String filePath = "testFile.txt";
        String content = "您好，这是一个测试文件。该文件用于测试。";

        createTestFile(filePath, content);
        List<Long> test = new ArrayList<>();
        test.add(4L);
        List<Long> res = Substring.find(filePath, "是一个");
        assertEquals(res, test);

        new File(filePath).delete();
    }
}