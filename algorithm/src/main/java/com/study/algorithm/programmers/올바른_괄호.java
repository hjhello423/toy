package com.study.algorithm.programmers;

import java.util.Stack;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/12909
 */
public class 올바른_괄호 {

    public static void main(String[] args) {
        boolean solution = solution("(()(");
        System.out.println(solution);
    }

    public static boolean solution(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char str = s.charAt(i);

            if (stack.isEmpty()) {
                stack.push(str);
            } else if (str == '(') {
                stack.push(str);
            } else {
                stack.pop();
            }
        }

        return stack.isEmpty();
    }

}
