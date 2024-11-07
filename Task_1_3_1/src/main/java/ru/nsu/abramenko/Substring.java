package ru.nsu.abramenko;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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

        try (FileInputStream fis = new FileInputStream(inputFilePath)) {

            StringBuilder batchBuilder = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                batchBuilder.append(new String(buffer, 0, bytesRead, StandardCharsets.UTF_8));

                if (batchBuilder.length() > 10000) {
                    matches.addAll(findAll(batchBuilder.toString(), patternString));
                    batchBuilder.setLength(0);
                }
            }

            if (!batchBuilder.isEmpty()) {
                matches.addAll(findAll(batchBuilder.toString(), patternString));
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

    private static final int X = 1103;
    private static final int MOD = 1093;

    private static int hash(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            res = (res * X % MOD + value(str.charAt(i))) % MOD;
        }
        return res;
    }

    private static int value(char sym) {
        return (int) sym;
    }

    private static int power(int n, int m) {
        int res = 1;
        for (int i = 0; i < m; i++) {
            res = res * n % MOD;
        }
        return res % MOD;
    }

    /**
     * Реализация полимиально хэширования для поиска подстроки.
     *
     * @param text строка
     * @param pattern подстрока
     * @return массив начал вхождений подстроки в строке
     */
    public static List<Integer> findAll(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int b = pattern.length();
        int patternHash = hash(pattern);
        int xp = power(X, b - 1);
        int h = hash(text.substring(0, b));
        if (h == patternHash) {
            matches.add(0);
        }
        for (int i = 1; i < n - b + 1; i++) {
            int val = (value(text.charAt(i - 1)) * xp) % MOD;
            h = ((h - (value(text.charAt(i - 1)) * xp) % MOD) + MOD) % MOD;
            h = h * X % MOD;
            h = ((h + value(text.charAt(i - 1 + b))) + MOD) % MOD;
            if (h == patternHash) {
                matches.add(i);
            }
        }
        return matches;
    }
}