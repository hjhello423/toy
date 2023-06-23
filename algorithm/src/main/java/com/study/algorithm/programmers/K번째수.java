package com.study.algorithm.programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42748
 */
public class K번째수 {

    public static void main(String[] args) {
        int[] solution = solution(new int[]{1, 5, 2, 6, 3, 7, 4}, new int[][]{{2, 5, 3}, {4, 4, 1}, {1, 7, 3}});
        System.out.println(Arrays.toString(solution));
    }

    public static int[] solution(int[] array, int[][] commands) {
        List<Integer> answer = new ArrayList<>();
        List<Integer> list = Arrays.stream(array)
                .boxed()
                .collect(Collectors.toList());


        for (int i = 0; i < commands.length; i++) {
            int I = commands[i][0] - 1;
            int J = commands[i][1] - 1;
            int K = commands[i][2] - 1;

            List<Integer> subList = new ArrayList<>(list.subList(I, J + 1));
            Collections.sort(subList);


            Integer find = subList.get(K);
            answer.add(find);
        }

        return answer.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
