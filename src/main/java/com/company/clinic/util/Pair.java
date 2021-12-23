package com.company.clinic.util;

public class Pair<T> {
    private final T min;
    private final T max;

    public Pair(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
