package com.github.hjdeepsleep.toy.mordern_java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.hjdeepsleep.toy.mordern_java.Type.*;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    List<Player> players;

    @BeforeEach
    public void before() {
        players = new ArrayList<>();

        players.add(new Player("a", true, 10, SOCCER));
        players.add(new Player("b", false, 40, BASEBALL));
        players.add(new Player("c", true, 20, SOCCER));
        players.add(new Player("d", false, 50, BASEBALL));
        players.add(new Player("e", true, 30, BASKETBALL));
    }

    @Test
    public void basic() throws Exception {
        //given
        List<String> result =
                players.stream() //스트립 추출
                        .filter(player -> player.getHeight() > 30) //무게가 30 이상인 선수 필터링
                        .map(Player::getName) //선수 치름 추출
                        .limit(2) //2개의 결과 선택
                        .collect(toList()); //결과를 리스트로 저장

        //then
        for (String name : result) {
            System.out.println(name);
        }
    }

    @DisplayName("스트림은 한번만 사용 가능 하다.")
    @Test
    public void use_one() throws Exception {
        //given
        Stream<Player> stream = players.stream();

        //when
        stream.forEach(System.out::println);

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> {
                    stream.forEach(System.out::println);
                });
    }

    @DisplayName("요소 건너 뛰기")
    @Test
    public void skip() throws Exception {
        List<String> collect = players.stream()
                .map(Player::getName)
                .skip(2)
                .collect(toList());

        assertFalse(collect.contains("a"));
        assertFalse(collect.contains("b"));
    }

    @DisplayName("매핑")
    @Test
    public void map() throws Exception {
        List<String> words = Arrays.asList("hi", "hello", "apple", "lion");

        List<Integer> collect = words.stream()
                .map(String::length)
                .collect(toList());
    }

    @DisplayName("스트림 평면화")
    @Test
    public void flatMap() throws Exception {
        List<String> words = Arrays.asList("hello", "world");

        List<String[]> collect = words.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(toList());

        List<String> collect2 = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());
    }

    @DisplayName("리듀스")
    @Test
    public void reduce() throws Exception {
        //given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        //when
        Integer result1 = numbers.stream()
                .reduce(0, (a, b) -> a + b); //초기값 0으로 지정

        Integer result2 = numbers.stream()
                .reduce(0, Integer::sum);

        //then
        assertTrue(result1 == 15);
        assertTrue(result2 == 15);
    }

    @DisplayName("리듀스 - 초기 값이 없을때")
    @Test
    public void reduce_optional() throws Exception {
        //given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        //when

        Optional<Integer> result = numbers.stream()
                .reduce( Integer::sum); //초기값 지정 안할 경우 Optional 반환

        //then
        assertTrue(result.get() == 15);
    }
}