package ru.nsu.abramenko;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
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
    void baseTest() {
        String filePath = "input.txt";
        String content = "абракадабра";
        String pattern = "бра";

        try {
            createTestFile(filePath, content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Substring.find(filePath, pattern);

    }

    @Test
    @DisplayName("SubstringTests")
    public void testFindSubstringExists() throws IOException {
        String filePath = "testFile.txt";
        String content = "Hello, this is a test file. This file is for testing.";
        String pattern = "test";

        createTestFile(filePath, content);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Substring.find(filePath, pattern);

        String expectedOutput = "[17, 45]";
        assertTrue(outContent.toString().contains(expectedOutput));
        System.setOut(System.out);

        new File(filePath).delete();
    }

    /**
     * Тест, в котором не существует подстроки.
     *
     * @throws IOException ошибка при чтении/записис
     */
    @Test
    @DisplayName("NotExists")
    public void testFindSubstringNotExists() throws IOException {
        String filePath = "testFile.txt";
        String content = "Hello, this is a test file. This file is for testing.";
        String pattern = "nonexistent";

        createTestFile(filePath, content);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Substring.find(filePath, pattern);

        String expectedOutput = "Такой подстроки не существует";
        assertTrue(outContent.toString().contains(expectedOutput));

        System.setOut(System.out);

        new File(filePath).delete();
    }
}