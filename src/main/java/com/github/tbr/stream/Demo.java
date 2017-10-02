package com.github.tbr.stream;

import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Demo {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH));
        menu.stream().forEach(System.out::println);
        List<String> names3Limit = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).limit(3).collect(Collectors.toList());
        System.out.println(names3Limit);
        List<String> names3Skip = menu.stream().filter(d -> d.getCalories() > 300).map(Dish::getName).skip(3).collect(Collectors.toList());
        System.out.println(names3Skip);
        List<Dish> vegetarins = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        System.out.println(vegetarins);
        Arrays.asList(1, 2, 3, 4, 5, 6).stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);
        List<Integer> dishNameLength = menu.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
        System.out.println(dishNameLength);

        // flat map
        Arrays.asList("hello", "world").stream().map(w -> w.split("")).flatMap(Arrays::stream).forEach(System.out::println);

        List<Integer> numList1 = Arrays.asList(1, 2, 3);
        List<Integer> numList2 = Arrays.asList(3, 4);
        List<int[]> pairs = numList1.stream().flatMap(i -> numList2.stream().filter(j -> (i + j) % 3 == 0).map(j -> new int[]{i, j})).collect(Collectors.toList());
        pairs.stream().forEach(pair -> {
            for (int i : pair) {
                System.out.print(i + ",");
            }
            System.out.println();

        });

        System.out.println(menu.stream().anyMatch(Dish::isVegetarian));
        System.out.println(menu.stream().allMatch(dish -> dish.getCalories() > 300));
        System.out.println(menu.stream().noneMatch(dish -> dish.getCalories() >= 1300));
        menu.stream().filter(Dish::isVegetarian).findAny().ifPresent(dish -> System.out.println(dish));
        menu.stream().filter(Dish::isVegetarian).findFirst().ifPresent(dish -> System.out.println(dish));
        Stream.iterate(0, n -> n + 2).limit(10).forEach(System.out::println);
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).map(t -> t[0]).limit(10).forEach(System.out::println);
        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        IntStream.generate(() -> 1).limit(10).forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

        System.out.println(menu.stream().count());
        System.out.println(menu.stream().collect(Collectors.counting()));

        Comparator<Dish> dischCalorComparator = Comparator.comparingInt(Dish::getCalories);
        menu.stream().collect(Collectors.maxBy(dischCalorComparator)).ifPresent(System.out::println);
        System.out.println(menu.stream().collect(Collectors.summingInt(Dish::getCalories)));
        System.out.println(menu.stream().collect(Collectors.summarizingInt(Dish::getCalories)));
        System.out.println(menu.stream().map(Dish::getName).collect(Collectors.joining(",")));
        System.out.println(menu.stream().collect(Collectors.groupingBy(Dish::getType)));

        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
                Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700) return
                            CaloricLevel.NORMAL;
                    else return CaloricLevel.FAT;
                }));
        System.out.println(dishesByCaloricLevel);
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeAndCaloricLevel = menu.stream().collect(
                Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return
                                    CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })));
        System.out.println(dishesByTypeAndCaloricLevel);
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = menu.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType,
                        Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))
                ));
        System.out.println(mostCaloricByType);

        Map<Dish.Type, Dish> mostCaloricByTypeDish = menu.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get
                        )
                ));
        System.out.println(mostCaloricByTypeDish);
        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType,
                        Collectors.summingInt(Dish::getCalories)
                        )
                );
        System.out.println(totalCaloriesByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType = menu.stream()
                .collect(
                        Collectors.groupingBy(
                                Dish::getType,
                                Collectors.mapping(
                                        dish -> {
                                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                            else if (dish.getCalories() <= 700) return
                                                    CaloricLevel.NORMAL;
                                            else return CaloricLevel.FAT;
                                        },
                                        Collectors.toSet()
                                )
                        )
                );
        System.out.println(caloricLevelsByType);

        Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByTypeHash = menu.stream()
                .collect(
                        Collectors.groupingBy(
                                Dish::getType,
                                Collectors.mapping(
                                        dish -> {
                                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                            else if (dish.getCalories() <= 700) return
                                                    CaloricLevel.NORMAL;
                                            else return CaloricLevel.FAT;
                                        },
                                        Collectors.toCollection(HashSet::new)
                                )
                        )
                );
        System.out.println(caloricLevelsByTypeHash);
    }
}

