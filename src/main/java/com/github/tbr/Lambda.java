package com.github.tbr;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 */
public class Lambda {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        final String separator = ",";
        Arrays.asList("a", "b", "c", "d").forEach((String e) -> System.out.println(e + separator));
        List<String> list = Arrays.asList("c", "a", "d", "b");
        System.out.println(list);
        list.sort((e1, e2) -> e1.compareTo(e2));
        System.out.println(list);
    }
}
