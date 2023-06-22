package com.study.algorithm.programmers;

import java.util.Stack;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12906
 */
public class 같은숫자는싫어 {

    public static void main(String[] args) {

        solution(new int[]{1, 1, 3, 3, 0, 1, 1});
    }

    public static int[] solution(int[] arr) {
        int[] answer = {};

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < arr.length; i++) {
            if (stack.isEmpty()) {
                stack.push(arr[i]);
            } else {
                Integer top = stack.peek();
                if (top != arr[i]) {
                    stack.push(arr[i]);
                }
            }
        }

        answer = new int[stack.size()];
        for (int i = answer.length - 1; i >= 0; i--) {
            Integer pop = stack.pop();
            answer[i] = pop;
        }


        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
        System.out.println("Hello Java");

        return answer;
    }
}
