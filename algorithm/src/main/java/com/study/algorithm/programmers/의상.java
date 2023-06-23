package com.study.algorithm.programmers;

import java.util.HashMap;

/**
 * 의상
 * https://school.programmers.co.kr/learn/courses/30/lessons/42578
 */
public class 의상 {

    public static void main(String[] args) {
        int solution = solution(new String[][]{{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"}});
        System.out.println(solution);
    }

    public static int solution(String[][] clothes) {
        int answer = 1;
        HashMap<String, Integer> hash = new HashMap<>();

        for (int i = 0; i < clothes.length; i++) {
            String type = clothes[i][1];
            hash.put(type, hash.getOrDefault(type, 0) + 1);
        }

        for (int value : hash.values()) {
            answer *= (value + 1);
        }

        return answer -1;
    }
}
