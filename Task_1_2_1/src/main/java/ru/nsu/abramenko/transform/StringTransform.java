package ru.nsu.abramenko.transform;

public class StringTransform implements Transform<String>{
    @Override
    public String transform(String str) {
        return str;
    }
}