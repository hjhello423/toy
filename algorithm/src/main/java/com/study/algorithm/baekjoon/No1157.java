package com.study.algorithm.baekjoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 단어 공부
 * https://www.acmicpc.net/problem/1157
 *
 * 문제
 * 알파벳 대소문자로 된 단어가 주어지면, 이 단어에서 가장 많이 사용된 알파벳이 무엇인지 알아내는 프로그램을 작성하시오.
 * 단, 대문자와 소문자를 구분하지 않는다.
 *
 * 입력
 * 첫째 줄에 알파벳 대소문자로 이루어진 단어가 주어진다. 주어지는 단어의 길이는 1,000,000을 넘지 않는다.
 *
 * 출력
 * 첫째 줄에 이 단어에서 가장 많이 사용된 알파벳을 대문자로 출력한다. 단, 가장 많이 사용된 알파벳이 여러 개 존재하는 경우에는 ?를 출력한다.
 *
 */
public class No1157 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String input = br.readLine().toUpperCase();

        int[] count = new int[26];
        int maxCount = 0;
        char maxChar = '?';

        for (int i = 0; i < input.length(); i++) {
            int idx = input.charAt(i) - 'A';
            count[idx] = count[idx] + 1;

            if (maxCount < count[idx]) {
                maxCount = count[idx];
                maxChar = input.charAt(i);
            } else if (maxCount == count[idx]) {
                maxChar = '?';
            }
        }

        bw.write(maxChar);
        bw.flush();
        bw.close();
        br.close();
    }
}
