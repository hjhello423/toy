package com.study.algorithm.programmers;

/**
 * 게임 맵 최단거리
 * https://school.programmers.co.kr/learn/courses/30/lessons/1844
 */
public class 게임_맵_최단거리 {
    private int[][] maps;
    private int[][] visited;
    private int count = 0;
    private int[] xIdx = {0, 1, 0, -1};
    private int[] yIdx = {1, 0, -1, 0};

    public static void main(String[] args) {
        int solution = new 게임_맵_최단거리().solution(new int[][]{
                {1, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 0, 0, 1}
        });
//        int solution = new 게임_맵_최단거리().solution(new int[][]{
//                {1, 0, 1, 1, 1},
//                {1, 0, 1, 0, 1},
//                {1, 0, 1, 1, 1},
//                {1, 1, 1, 0, 0},
//                {0, 0, 0, 0, 1}
//        });
        System.out.println(solution);
    }

    public int solution(int[][] maps) {
        this.maps = maps;
        this.visited = new int[maps.length][maps[0].length];

        dfs(0, 0);

        if (count == 0) {
            return -1;
        }
        return count + 1;
    }

    private void dfs(int x, int y) {
        if (x == maps.length - 1 && y == maps[0].length - 1) {
            if (count == 0) {
                count = visited[x][y];
            } else {
                count = Math.min(count, visited[x][y]);
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            int nextX = x + xIdx[i];
            int nextY = y + yIdx[i];

            if (nextX < 0 || nextY < 0 || nextX >= maps.length || nextY >= maps[0].length) {
                continue;
            }

            if (maps[nextX][nextY] == 0 || visited[nextX][nextY] != 0) {
                continue;
            }

            visited[nextX][nextY] = visited[x][y] + 1;
            dfs(nextX, nextY);
            visited[nextX][nextY] = 0;
        }
    }
}
