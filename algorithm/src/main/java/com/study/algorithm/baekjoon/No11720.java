package com.study.algorithm.baekjoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 숫자의 합
 * https://www.acmicpc.net/problem/11720
 *
 * 문제
 * N개의 숫자가 공백 없이 쓰여있다. 이 숫자를 모두 합해서 출력하는 프로그램을 작성하시오.
 *
 * 입력
 * 첫째 줄에 숫자의 개수 N (1 ≤ N ≤ 100)이 주어진다. 둘째 줄에 숫자 N개가 공백없이 주어진다.
 *
 * 출력
 * 입력으로 주어진 숫자 N개의 합을 출력한다.
 */
public class No11720 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        String inputNumber = br.readLine();

        char[] numbers = inputNumber.toCharArray();

        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += numbers[i] - '0';
        }


        bw.write(String.valueOf(sum));
        bw.flush();
        br.close();
        bw.close();
    }
}
