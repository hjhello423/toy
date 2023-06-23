package com.study.algorithm.programmers;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 디스크 컨트롤러
 * https://school.programmers.co.kr/learn/courses/30/lessons/42627#qna
 */
public class 디스크_컨트롤러 {

    public static void main(String[] args) {
        int solution = solution(new int[][]{{1, 9}, {0, 3}, {2, 6}});
//        int solution = solution(new int[][]{{1, 9}, {0, 3}, {2, 6}, {0,4}});
        System.out.println(solution);
    }

    public static int solution(int[][] jobs) {
        int answer = 0;

        List<Job> list = new LinkedList<>();
        for (int i = 0; i < jobs.length; i++) {
            list.add(new Job(jobs[i][0], jobs[i][1]));
        }

        list.sort((o1, o2) -> {
            if (o1.requestTime == o2.requestTime) {
                return o1.compareTo(o2);
            }
            return Integer.compare(o1.requestTime, o2.requestTime);
        });

        Queue<Job> queue = new PriorityQueue<>();
        int time = 0;
        int count = 0;
        while (count < jobs.length) {
            for (int i = 0; i < list.size(); i++) {
                Job job = list.get(i);
                if (job.requestTime <= time) {
                    queue.add(job);
                    list.remove(job);
                    i--;
                }
            }

            if (queue.isEmpty()) {
                time++;
            } else {
                Job poll = queue.poll();
                answer += time + poll.workingTime - poll.requestTime;
                time += poll.workingTime;
                count++;
            }
        }

        return answer / count;
    }

    private static class Job implements Comparable<Job> {
        private int requestTime;
        private int workingTime;

        public Job(int requestTime, int workingTime) {
            this.requestTime = requestTime;
            this.workingTime = workingTime;
        }

        @Override
        public int compareTo(Job o) {
            if (this.workingTime == o.workingTime) {
                return 0;
            }
            return this.workingTime - o.workingTime;
        }
    }
}
