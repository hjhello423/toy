package com.study.algorithm.programmers;

import java.util.PriorityQueue;

/**
 * 더 맵게
 * https://school.programmers.co.kr/learn/courses/30/lessons/42626
 */
public class 더_맵게 {

    public static void main(String[] args) {
        int solution = solution(new int[]{1, 2, 3, 9, 10, 12}, 7);
//        int solution = solution(new int[]{}, 7);
//        int solution = solution(new int[]{18, 20, 21, 24}, 7);
//        int solution = solution(new int[]{0, 1}, 2);
        System.out.println(solution);
    }

    public static int solution(int[] scoville, int K) {
        int answer = 0;

        if (scoville.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : scoville) {
            queue.add(i);
        }

        int minScoville = queue.peek();
        while (minScoville < K) {
            if (queue.size() == 1) {
                return -1;
            }
            answer++;
            Integer first = queue.poll();
            Integer second = queue.poll();

            Integer sum = first + (second * 2);
            queue.add(sum);
            minScoville = queue.peek();

        }

        return answer;
    }
}
