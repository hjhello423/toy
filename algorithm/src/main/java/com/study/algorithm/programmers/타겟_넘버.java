package com.study.algorithm.programmers;

/**
 * 타겟 넘버
 * https://school.programmers.co.kr/learn/courses/30/lessons/43165
 */
public class 타겟_넘버 {
    private int answer = 0;
    private int[] numbers;
    private int target;

    public static void main(String[] args) {
        int solution = new 타겟_넘버().solution(new int[]{1, 1, 1, 1, 1}, 3);
        System.out.println(solution);
    }

    public int solution(int[] numbers, int target) {
        this.numbers = numbers;
        this.target = target;
        dfs(0, 0);
        return answer;
    }

    private void dfs(int idx, int sum) {
        if (idx == this.numbers.length) {
            if (sum == target) {
                answer++;
            }
            return;
        }

        dfs(idx + 1, sum + numbers[idx]);
        dfs(idx + 1, sum - numbers[idx]);
    }
}
