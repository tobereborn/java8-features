package com.github.tbr;

import com.github.tbr.behavior.parameterization.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FunctionTest {

    public static <T> void forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> function) {
        List<R> result = new ArrayList<R>();
        for (T t : list) {
            result.add(function.apply(t));
        }
        return result;
    }

    public static void localVariable() {
        Integer port = 1234;
        Runnable r = () -> System.out.println(port);
//        port=2345;
        r.run();

    }

    public static void methodReference() {
        List<Integer> list = Arrays.asList(4, 2, 3, 1, 6);
//        list.sort((a, b) -> a.compareTo(b));
        list.sort(Integer::compareTo);
        System.out.println(list);

        Supplier<Apple> s=Apple::new;
        Apple a=s.get();
        System.out.println(a);
    }

    public static void main(String[] args) {
        FunctionTest.forEach(Arrays.asList(1, 2, 3, 4, 5), (Integer i) -> System.out.println(i));
        List<Integer> list = FunctionTest.map(Arrays.asList("abc", "b", "defh"), (String s) -> s.length());
        System.out.println(list);
        FunctionTest.localVariable();
        FunctionTest.methodReference();
    }
}
