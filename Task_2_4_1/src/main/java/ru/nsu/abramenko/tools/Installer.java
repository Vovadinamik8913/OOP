package ru.nsu.abramenko.tools;

import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDate;

public class Installer {
    public static final String labs = "src/main/resources/labs/";

    @SneakyThrows
    public static boolean download(String repo, String folder, String branch) {
        ProcessBuilder processBuilder = new ProcessBuilder("git",
                "clone", "-b", branch, repo, labs + folder);
        Process process = processBuilder.start();
        return process.waitFor() == 0;
    }

    public static boolean switchToDeadlineCommit(String repoPath, LocalDate deadline, int index) throws IOException, InterruptedException {
            ProcessBuilder resetProcess = new ProcessBuilder(
                    "git",
                    "reset",
                    "--hard"
            );
            resetProcess.directory(new java.io.File(repoPath));
            resetProcess.start();

            ProcessBuilder commitProcess = new ProcessBuilder(
                    "git",
                    "log",
                    "--until=" + deadline + " 23:59:59",
                    "--format=%H",
                    "-" + index
            );
            commitProcess.directory(new java.io.File(repoPath));
            Process commit = commitProcess.start();

            String[] commitHashes = new String(commit.getInputStream().readAllBytes()).trim().split("\n");

            if (commitHashes[index - 1].isEmpty()) {
                return false;
            }
            String commitHash = commitHashes[index - 1];
            ProcessBuilder checkoutProcess = new ProcessBuilder("git", "checkout", "-f", commitHash);
            checkoutProcess.directory(new java.io.File(repoPath));
            return checkoutProcess.start().waitFor() == 0;
    }
}
