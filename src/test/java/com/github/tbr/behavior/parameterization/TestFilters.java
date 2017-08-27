package com.github.tbr.behavior.parameterization;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class TestFilters {
    private List<Apple> apples;

    @Before
    public void setUp() {
        this.apples = Arrays.asList(new Apple("Red", 50),
                new Apple("Green", 60),
                new Apple("Blue", 70)
        );
    }

    @Test
    public void testColorFilter() {
        List<Apple> expected = Arrays.asList(new Apple("Green", 60));
        List<Apple> actual = Filters.filter(apples,
                new Predicate<Apple>() {
                    @Override
                    public boolean test(Apple apple) {
                        return "Green".equals(apple.getColor());
                    }
                }
        );

        System.out.println(actual);
        Assert.assertThat(actual, is(expected));

        actual = Filters.filter(apples, (Apple t) -> "Green".equals(t.getColor()));
        System.out.println(actual);
        Assert.assertThat(actual, is(expected));
    }

}
