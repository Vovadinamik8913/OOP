package ru.nsu.abramenko;

import java.util.ArrayList;
import java.util.List;

/** realization of Rabin-Karp algorithm.
 *
 */
public class RabinKarp {
    private static final int X = 1103;
    private static final int XMOD = 1093;
    private static final int Y = 2477;
    private static final int YMOD = 1553;

    private static int hash(String str, int factor, int mod) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            res = (res * factor % mod + str.charAt(i)) % mod;
        }
        return res;
    }

    private static int power(int n, int m, int mod) {
        int res = 1;
        for (int i = 0; i < m; i++) {
            res = res * n % mod;
        }
        return res % mod;
    }

    private static int shift(int hash, char prev, char next, int np, int n, int mod) {
        int h = ((hash - (prev * np) % mod) + mod) % mod;
        h = h * n % mod;
        h = ((h + next) + mod) % mod;
        return h;
    }

    /**
     * Реализация полимиально хэширования для поиска подстроки.
     *
     * @param text строка
     * @param pattern подстрока
     * @return массив начал вхождений подстроки в строке
     */
    public static List<Integer> find(String text, String pattern) {
        List<Integer> matches = new ArrayList<>();
        int n = text.length();
        int b = pattern.length();
        int xPatternHash = hash(pattern, X, XMOD);
        int yPatternHash = hash(pattern, Y, YMOD);
        int xp = power(X, b - 1, XMOD);
        int yp = power(Y, b - 1, YMOD);
        int hx = hash(text.substring(0, b), X, XMOD);
        int hy = hash(text.substring(0, b), Y, YMOD);
        if (hx == xPatternHash && hy == yPatternHash) {
            matches.add(0);
        }
        for (int i = 1; i < n - b + 1; i++) {
            hx = shift(hx, text.charAt(i-1), text.charAt(i - 1 + b), xp, X, XMOD);
            hy = shift(hy, text.charAt(i-1), text.charAt(i - 1 + b), yp, Y, YMOD);
            if (hx == xPatternHash && hy == yPatternHash) {
                matches.add(i);
            }
        }
        return matches;
    }
}
