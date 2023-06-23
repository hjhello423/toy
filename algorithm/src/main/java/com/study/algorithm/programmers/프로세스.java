package com.study.algorithm.programmers;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://school.programmers.co.kr/learn/courses/30/lessons/42587
 */
public class 프로세스 {

    public static void main(String[] args) {
        int solution = solution(new int[]{2, 1, 3, 2}, 2);
        System.out.println(solution);
    }

    public static int solution(int[] priorities, int location) {
        int count = 0;
        int index = Integer.MIN_VALUE;
        boolean isRun = false;
        Queue<Process> process = new LinkedList<>();

        for (int i = 0; i < priorities.length; i++) {
            process.add(new Process(priorities[i], i));
        }

        while (index != location) {
            Process poll = process.poll();
            int priority = poll.priority;

            for (Process p : process) {
                if (p.priority > priority) {
                    process.add(poll);
                    isRun = false;
                    break;
                }
                isRun = true;
            }

            if (!isRun) {
                continue;
            }

            index = poll.location;
            count++;
        }

        return count;
    }

    private static class Process {
        int priority;
        int location;

        public Process(int priority, int location) {
            this.priority = priority;
            this.location = location;
        }
    }

}
