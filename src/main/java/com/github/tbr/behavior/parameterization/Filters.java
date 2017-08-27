package com.github.tbr.behavior.parameterization;

import java.util.ArrayList;
import java.util.List;

public class Filters {

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }

        return result;
    }

}
