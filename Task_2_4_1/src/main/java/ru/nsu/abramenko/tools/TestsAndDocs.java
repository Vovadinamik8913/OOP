package ru.nsu.abramenko.tools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Data;
import lombok.SneakyThrows;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Data
public class TestsAndDocs {
    private int passedTests;
    private int totalTests;
    private int ignoredTests;
    private String documentationExists;

    @SneakyThrows
    public void analyze(String testResPath, String documentationDir) {
        File testFile = new File(testResPath);
        if (!testFile.exists()) {
            totalTests = 0;
            passedTests = 0;
            ignoredTests = 0;
            documentationExists = "Missing";
            return;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document junitDoc = builder.parse(testFile);

        Element testSuite = (Element) junitDoc.getElementsByTagName("testsuite").item(0);
        totalTests = Integer.parseInt(testSuite.getAttribute("tests"));
        int failures = Integer.parseInt(testSuite.getAttribute("failures"));
        ignoredTests = Integer.parseInt(testSuite.getAttribute("skipped"));
        passedTests = totalTests - failures - ignoredTests;

        documentationExists = Files.exists(Path.of(documentationDir)) ? "Generated" : "Missing";
    }
}