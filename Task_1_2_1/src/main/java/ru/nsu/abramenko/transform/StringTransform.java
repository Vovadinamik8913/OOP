package ru.nsu.abramenko.transform;

/** from string to string.
 *
 */
public class StringTransform implements Transform<String> {
    @Override
    public String transform(String str) {
        return str;
    }
}