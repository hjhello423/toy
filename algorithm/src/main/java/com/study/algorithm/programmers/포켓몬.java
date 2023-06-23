package com.study.algorithm.programmers;

import java.util.HashMap;

/**
 * 폰켓몬
 * https://school.programmers.co.kr/learn/courses/30/lessons/1845
 */
public class 포켓몬 {

    public static void main(String[] args) {
        int solution = solution(new int[]{3, 1, 2, 3});
//        int solution = solution(new int[]{1, 1, 1, 1});
        System.out.println(solution);
    }

    public static int solution(int[] nums) {
        int getNum = nums.length / 2;

        HashMap<Integer, Integer> poketmon = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (poketmon.get(nums[i]) == null) {
                poketmon.put(nums[i], 1);
            } else {
                poketmon.put(nums[i], poketmon.get(nums[i]) + 1);
            }
        }

        if (poketmon.size() >= getNum) {
            return getNum;
        }

        return poketmon.size();
    }
}
