package com.github.hjdeepsleep.toy.lambda_study;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @DisplayName("Apple 정렬 - 익명함수 이용 방식")
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

    @DisplayName("Apple 정렬 - 형식 추론을 이용한 개선")
    @Test
    public void apple_sort_inference() throws Exception {
        //given
        List<Apple> copy1 = new ArrayList<>(apples);
        List<Apple> copy2 = new ArrayList<>(apples);

        Comparator<Apple> byWeight = (Apple a1, Apple a2) -> //형식 추론 X
                a1.getWeight().compareTo(a2.getWeight());

        Comparator<Apple> byWeightInference = (a1, a2) -> // 형식 추론
                a1.getWeight().compareTo(a2.getWeight());

        //when
        copy1.sort(byWeight);
        copy2.sort(byWeightInference);

        //then
        assertTrue(copy1. equals(copy2));
    }

    @DisplayName("람다에서 확인된 예외 처리 하기(AppleFunctionInterface)")
    @Test
    public void lambda_exception() throws Exception {
        Function<Integer, Apple> f = (Integer i) -> {
            try {
                return new Apple(i);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException();
            }
        };
    }
}