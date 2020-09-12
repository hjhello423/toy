package com.github.hjdeepsleep.toy.mordern_java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.hjdeepsleep.toy.mordern_java.Type.*;
import static java.util.stream.Collectors.toList;

class PlayerTest {

    List<Player> players;

    @BeforeEach
    public void before() {
        players = new ArrayList<>();

        players.add(new Player("a", true, 10, SOCCER));
        players.add(new Player("b", false, 20, BASEBALL));
        players.add(new Player("c", true, 30, SOCCER));
        players.add(new Player("d", false, 40, BASEBALL));
        players.add(new Player("e", true, 50, BASKETBALL));
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
}