package ru.nsu.abramenko.transform;

public interface Transform<T> {
    T transform(String str);
}