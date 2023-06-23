package com.study.algorithm.programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 베스트앨범
 * https://school.programmers.co.kr/learn/courses/30/lessons/42579
 */
public class 베스트앨범 {

    public static void main(String[] args) {
        int[] solution = solution(new String[]{"classic", "pop", "classic", "classic", "pop"}, new int[]{500, 600,
                150, 800, 2500});
        System.out.println(Arrays.toString(solution));

    }

    public static int[] solution(String[] genres, int[] plays) {
        List<Integer> answer = new ArrayList<>();
        Map<String, Integer> hash = new HashMap<>();
        List<Music> musics = new ArrayList<>();

        for (int i = 0; i < genres.length; i++) {
            hash.put(genres[i], hash.getOrDefault(genres[i], 0) + plays[i]);
            musics.add(new Music(genres[i], plays[i], i));
        }
        List<String> sorted = hash.keySet()
                .stream()
                .sorted((o1, o2) -> hash.get(o2).compareTo(hash.get(o1)))
                .collect(Collectors.toList());

        for (String type : sorted) {
            List<Music> collect = musics.stream()
                    .filter(it -> type.equals(it.type))
                    .sorted((o1, o2) -> Integer.compare(o2.play, o1.play))
                    .limit(2)
                    .collect(Collectors.toList());

            collect.forEach(it -> answer.add(it.index));
        }

        return answer.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private static class Music {
        private String type;
        private int play;
        private int index;

        public Music(String type, int play, int index) {
            this.type = type;
            this.play = play;
            this.index = index;
        }

    }

}
