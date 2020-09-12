package com.github.hjdeepsleep.toy.mordern_java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

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
        assertTrue(copy1.equals(copy2));
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

    @DisplayName("람다의 자유 변수 사용")
    @Test
    public void capturing() throws Exception {
        //given
        final int number = 10; //자유 변수

        //when
        Runnable r = () -> System.out.println(number); //자유 변수 사용

        //then
        r.run();
    }

    @DisplayName("람다에서 사용한 자유 변수의 제약 - final")
    @Test
    public void capturing_val() throws Exception {
        //given
        int number = 10; //자유 변수

        //when
        Runnable r = () -> System.out.println(number); //자유 변수 사용

        //then
//        number = 20; //람다에서 사용한 자유 변수의 재 초기화로 인해 compile 불가능
    }


    @DisplayName("메서드 참조 이용")
    @Test
    public void method_ref() throws Exception {
        //given
        List<String> target1 = Arrays.asList("a", "c", "d", "b");
        List<String> target2 = Arrays.asList("a", "c", "d", "b");

        //when
        target1.sort((s1, s2) -> s1.compareTo(s2));
        target2.sort(String::compareTo); //메서드 참조 사용

        //then
        assertTrue(target1.equals(target2));
    }

    @DisplayName("map을 이용한 apple generator")
    @Test
    public void generate_apple() throws Exception {
        //given
        List<Integer> weights = Arrays.asList(1, 3, 5, 7, 9);

        //when
        List<Apple> apples = map(weights, Apple::new);

        //then
        System.out.println(apples);
        assertTrue(apples.contains(new Apple(1)));
    }

    private List<Apple> map(List<Integer> list, Function<Integer, Apple> f) {
        List<Apple> result = new ArrayList<>();

        for (Integer i : list) {
            result.add(f.apply(i));
        }

        return result;
    }
}