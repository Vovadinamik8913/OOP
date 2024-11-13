package ru.nsu.abramenko;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Класс для поиска подстроки.
 */
public class Substring {
    /**
     * Метод считывает строку из файла и ищет подстроку с помощью Бойера-Мура.
     *
     * @param filePath путь к файлу
     * @param patternString нужная подстрока
     */

    public static List<Integer> find(
            @NotNull String filePath, @NotNull String patternString) throws RuntimeException {
        List<Integer> matches = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            StringBuilder builder = new StringBuilder();
            String line;
            var ref = new Object() {
                int shift = 0;
            };
            char[] buffer = new char[4096];
            int bytesRead;
            while ((bytesRead = br.read(buffer)) != -1) {
                builder.append(buffer, 0, bytesRead);

                if (builder.length() > 10000) {
                    List<Integer> res = RabinKarp.find(builder.toString(), patternString);
                    res.replaceAll((val) -> val += ref.shift);
                    matches.addAll(res);
                    int firstCharIndex = res.isEmpty() ? 0 :
                            Math.max(0, builder.length() - patternString.length() + 1);
                    builder.delete(0, firstCharIndex);
                    ref.shift += firstCharIndex;
                }
            }
            if (!builder.isEmpty() &&  builder.length() > patternString.length()) {
                List<Integer> res = RabinKarp.find(builder.toString(), patternString);
                res.replaceAll((val) -> val += ref.shift);
                matches.addAll(res);
            }
            return matches;

        } catch (IOException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }
}