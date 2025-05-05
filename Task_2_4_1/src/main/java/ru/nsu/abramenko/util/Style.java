package ru.nsu.abramenko.util;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Style {
    @SneakyThrows
    public static boolean check(String taskPath) {
        String buildScript = """
            plugins {
                id 'java'
                id 'checkstyle'
            }
            
            repositories {
                mavenCentral()
            }
            
            checkstyle {
                toolVersion = '10.12.1'
                configFile = file("config/checkstyle/checkstyle.xml")
                maxWarnings = 0
                ignoreFailures = true
            }
            
            tasks.withType(Checkstyle) {
                reports {
                    xml.required = true
                    html.required = true
                }
            }
            
            sourceSets {
                main {
                    java {
                        srcDirs = ['src/main/java', 'src']
                    }
                }
            }
        """;

        Files.write(Paths.get(taskPath, "build.gradle"), buildScript.getBytes());

        Files.createDirectories(Paths.get(taskPath, "gradle", "wrapper"));
        Files.copy(Paths.get("gradle/wrapper/gradle-wrapper.jar"),
                Paths.get(taskPath, "gradle/wrapper/gradle-wrapper.jar"),
                StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get("gradle/wrapper/gradle-wrapper.properties"),
                Paths.get(taskPath, "gradle/wrapper/gradle-wrapper.properties"),
                StandardCopyOption.REPLACE_EXISTING);
        Files.copy(Paths.get("gradlew"),
                Paths.get(taskPath, "gradlew"),
                StandardCopyOption.REPLACE_EXISTING);

        new File(taskPath + "/gradlew").setExecutable(true);

        ProcessBuilder pb = new ProcessBuilder(
                "./gradlew",
                "checkstyleMain",
                "--no-daemon",
                "--offline",
                "--warning-mode", "none"
        );
        pb.directory(new File(taskPath));

        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0 || exitCode == 1;
        } catch (Exception e) {
            System.err.println("Style check failed: " + e.getMessage());
            return false;
        }
    }
}
