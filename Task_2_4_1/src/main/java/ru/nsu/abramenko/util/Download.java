package ru.nsu.abramenko.util;

import lombok.SneakyThrows;

public class Download {
    public static final String labs = "src/main/resources/labs/";

    @SneakyThrows
    public static boolean download(String repo, String folder, String branch) {
        ProcessBuilder processBuilder = new ProcessBuilder("git",
                "clone", "-b", branch, repo, labs + folder);
        Process process = processBuilder.start();
        int exitCode = process.waitFor();
        return exitCode == 0;
    }
}
