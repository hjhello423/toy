package com.study.algorithm.baekjoon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
