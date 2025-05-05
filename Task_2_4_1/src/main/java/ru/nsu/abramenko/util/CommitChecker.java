package ru.nsu.abramenko.util;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CommitChecker {

    @SneakyThrows
    public static CommitDates getCommitsDates(String labDir) {
        CommitDates dates = new CommitDates();
        dates.setLastCommit(getLastCommitDate(labDir));
        dates.setFirstCommit(getFirstCommitDate(labDir));
        return dates;
    }

    @SneakyThrows
    private static LocalDate getLastCommitDate(String projectDir) {
        return getGitDate(projectDir, "git log -1 --format=%cd --date=iso");
    }

    @SneakyThrows
    private static LocalDate getFirstCommitDate(String projectDir) {
        return getGitDate(projectDir, "git log --reverse --format=%cd --date=iso | head -1");
    }

    @SneakyThrows
    private static LocalDate getGitDate(String projectDir, String command) {
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
        pb.directory(new File(projectDir));

        Process process = pb.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );

        String dateStr = reader.readLine();
        if (dateStr == null || dateStr.isEmpty()) {
            return LocalDate.now();
        }

        return ZonedDateTime.parse(
                dateStr,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
        ).toLocalDate();
    }

    @Setter
    @Getter
    public static class CommitDates {
        private LocalDate firstCommit;
        private LocalDate lastCommit;
    }
}