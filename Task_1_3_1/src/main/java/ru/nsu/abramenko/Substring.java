package ru.nsu.abramenko;


import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.List;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;

/**
 * Класс для поиска подстроки.
 */
public class Substring {
    /**
     * Метод считывает строку из файла и ищет подстроку с помощью Бойера-Мура.
     *
     * @param inputFilePath путь к файлу
     * @param patternString нужная подстрока
     */

    public static List<Integer> find(
            @NotNull String inputFilePath, @NotNull String patternString) throws RuntimeException {
        List<Integer> matches = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {

            StringBuilder batchBuilder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            var ref = new Object() {
                int shift = 0;
            };
            while ((bytesRead = fis.read(buffer)) != -1) {
                batchBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));

                if (batchBuilder.length() > 10000) {
                    List<Integer> res = RabinKarp.find(batchBuilder.toString(), patternString);
                    res.replaceAll((val) -> val += ref.shift);
                    matches.addAll(res);
                    int firstCharIndex = res.isEmpty() ? 0 :
                            Math.max(0, batchBuilder.length() - patternString.length() + 1);
                    batchBuilder.delete(0, firstCharIndex);
                    ref.shift += firstCharIndex;
                }
            }

            if (!batchBuilder.isEmpty() &&  batchBuilder.length() > patternString.length()) {
                List<Integer> res = RabinKarp.find(batchBuilder.toString(), patternString);
                res.replaceAll((val) -> val += ref.shift);
                matches.addAll(res);
            }
            return matches;
        } catch (IOException e) {
            throw  new RuntimeException(e.getMessage());
        }
    }
}