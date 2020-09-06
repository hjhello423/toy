package com.github.hjdeepsleep.toy.lambda_study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class AppleTest {

    List<Apple> apples;

    @BeforeEach
    public void before() {
        apples = new ArrayList<>();

        apples.add(new Apple(10));
        apples.add(new Apple(30));
        apples.add(new Apple(40));
        apples.add(new Apple(20));
    }

    @DisplayName("Apple 정렬 - 기존 방식")
    @Test
    public void apple_sort() throws Exception {
        Comparator<Apple> byWeight = new Comparator<Apple>() {
            @Override
            public int compare(Apple apple1, Apple apple2) {
                return apple1.getWeight()
                        .compareTo(apple2.getWeight());
            }
        };

        System.out.println("정렬 전 ----");
        for (Apple apple : apples) {
            System.out.println("무게 : " + apple.getWeight());
        }

        System.out.println("정렬 후 ----");
        apples.sort(byWeight);

        for (Apple apple : apples) {
            System.out.println("무게 : " + apple.getWeight());
        }
    }


    @DisplayName("Apple 정렬 - 람다로 개선")
    @Test
    public void apple_sort_lambda() throws Exception {
        Comparator<Apple> byWeight = (Apple a1, Apple a2) ->
                a1.getWeight().compareTo(a2.getWeight());

        System.out.println("정렬 전 ----");
        for (Apple apple : apples) {
            System.out.println("무게 : " + apple.getWeight());
        }

        System.out.println("정렬 후 ----");
        apples.sort(byWeight);

        for (Apple apple : apples) {
            System.out.println("무게 : " + apple.getWeight());
        }
    }
}