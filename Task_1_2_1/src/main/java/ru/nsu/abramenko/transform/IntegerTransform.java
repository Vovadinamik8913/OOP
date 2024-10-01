package ru.nsu.abramenko.transform;

public class IntegerTransform implements Transform<Integer>{

    @Override
    public Integer transform(String str) {
        if (str != null) {
            return Integer.parseInt(str);
        }
        return 0;
    }
}