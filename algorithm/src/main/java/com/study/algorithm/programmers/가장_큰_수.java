package com.study.algorithm.programmers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42746
 */
public class 가장_큰_수 {

    public static void main(String[] args) {
//        String solution = solution(new int[]{0, 3, 30, 34, 5, 9});
        String solution = solution(new int[]{0, 0});
        System.out.println(solution);
    }

    public static String solution(int[] numbers) {
        List<String> list = Arrays.stream(numbers)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());

        list.sort((o1, o2) -> {
            return (o2 + o1).compareTo(o1 + o2);
        });

        if ("0".equals(list.get(0))){
            return "0";
        }

        StringBuilder builder = new StringBuilder();
        list.forEach(builder::append);

        return builder.toString();
    }

}
