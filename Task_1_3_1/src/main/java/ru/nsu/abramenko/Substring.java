package ru.nsu.abramenko;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static void find(String inputFilePath, String patternString) {
        List<Integer> matches = new ArrayList<>();
        Set<Integer> match = new HashSet<>();
        try (FileInputStream fis = new FileInputStream(inputFilePath)) {

            StringBuilder batchBuilder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            int shift = 0;
            while ((bytesRead = fis.read(buffer)) != -1) {
                batchBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));

                if (batchBuilder.length() > 10000) {
                    List<Integer> res = RabinKarp.find(batchBuilder.toString(), patternString);
                    if (shift != 0) {
                        for (Integer val : res) {
                            val += shift;
                        }
                    }
                    shift += batchBuilder.length() - patternString.length();
                    matches.addAll(res);
                    batchBuilder.delete(0, batchBuilder.length() - patternString.length() + 1);
                }
            }

            if (!batchBuilder.isEmpty() &&  batchBuilder.length() > patternString.length()) {
                List<Integer> res = RabinKarp.find(batchBuilder.toString(), patternString);
                if (shift != 0) {
                    for (Integer val : res) {
                        val += shift;
                    }
                }
                matches.addAll(res);
            }

            if (!matches.isEmpty()) {
                System.out.print("[");
                for (int i = 0; i < matches.size(); i++) {
                    System.out.print(matches.get(i));
                    if (i < matches.size() - 1) {
                        System.out.print(", ");
                    }
                }
                System.out.println("]");
            } else {
                System.out.println("Такой подстроки не существует");
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}