package com.study.algorithm.programmers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * H-Index
 * https://school.programmers.co.kr/learn/courses/30/lessons/42747
 */
public class H_Index {

    public static void main(String[] args) {
//        int solution = solution(new int[]{3, 0, 6, 1, 5});
        int solution = solution(new int[]{1, 4, 5});
//        int solution = solution(new int[]{4, 3, 3, 3, 0, 0, 0});
        System.out.println(solution);
    }

    public static int solution(int[] citations) {
        int answer = 0;
        List<Integer> list = Arrays.stream(citations)
                .boxed()
                .sorted()
                .collect(Collectors.toList());

        for (int i = 0; i < list.size(); i++) {
            int h = list.size() - i;

            if (list.get(i) >= h) {
                answer = h;
                break;
            }

        }

        return answer;
    }
}
