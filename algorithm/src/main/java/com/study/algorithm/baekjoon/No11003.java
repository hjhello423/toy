package com.study.algorithm.baekjoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 최솟값 찾기
 * https://www.acmicpc.net/problem/11003
 *
 * 문제
 * N개의 수 A1, A2, ..., AN과 L이 주어진다.
 *
 * Di = Ai-L+1 ~ Ai 중의 최솟값이라고 할 때, D에 저장된 수를 출력하는 프로그램을 작성하시오. 이때, i ≤ 0 인 Ai는 무시하고 D를 구해야 한다.
 *
 * 입력
 * 첫째 줄에 N과 L이 주어진다. (1 ≤ L ≤ N ≤ 5,000,000)
 *
 * 둘째 줄에는 N개의 수 Ai가 주어진다. (-109 ≤ Ai ≤ 109)
 *
 * 출력
 * 첫째 줄에 Di를 공백으로 구분하여 순서대로 출력한다.
 */
public class No11003 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());

        Deque<Node> deque = new ArrayDeque<>();
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            int number = Integer.parseInt(st.nextToken());

            while (!deque.isEmpty() && deque.getLast().value > number) {
                deque.removeLast();
            }
            deque.addLast(new Node(i, number));

            if ((i - deque.getFirst().index) >= L) {
                deque.removeFirst();
            }
            bw.write(deque.getFirst().value + " ");

        }

        bw.flush();
        br.close();
        bw.close();
    }

    private static class Node {
        private int index;
        private int value;

        private Node(int index, int value) {
            this.index = index;
            this.value = value;
        }

    }
}
