package com.github.hjdeepsleep.toy.mordern_java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.hjdeepsleep.toy.mordern_java.SportType.*;
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
                .reduce(Integer::sum); //초기값 지정 안할 경우 Optional 반환

        //then
        assertTrue(result.get() == 15);
    }

    @DisplayName("리듀싱 요양")
    @Test
    public void reduce_요약() throws Exception {
        //when
        long countPlayer = players.stream()
                .collect(Collectors.counting());

        long countPlayer2 = players.stream()
                .count();

        //then
        assertTrue(countPlayer == 5);
        assertTrue(countPlayer == countPlayer2);
    }

    @DisplayName("통계 구하기")
    @Test
    public void reduce_통계() throws Exception {
        //given
        Comparator<Player> comparing = Comparator.comparing(Player::getHeight);

        //when
        Optional<Player> maxHeightPlayer = players.stream() //키가 가장 큰 회원
                .collect(Collectors.maxBy(comparing));

        Integer totalHeight = players.stream() //회원들의 키의 총 합
                .collect(Collectors.summingInt(Player::getHeight));

        IntSummaryStatistics statistics = players.stream() //회원 키에 관한 통계
                .collect(Collectors.summarizingInt(Player::getHeight));
        System.out.println(statistics);

        //then
        assertTrue(maxHeightPlayer.get().getHeight() == 50);
        assertTrue(totalHeight == 150);
        assertTrue(statistics.getMax() == 50 && statistics.getCount() == 5);
    }

    @DisplayName("문자열 연산")
    @Test
    public void string_연산() throws Exception {
        //when
        String name1 = players.stream()
                .map(Player::getName)
                .collect(Collectors.joining());

        String name2 = players.stream()
                .map(Player::getName)
                .collect(Collectors.joining(", ")); //delimiter 추가 가능

        //then
        assertTrue(name1.equals("abcde"));
        assertTrue(name2.equals("a, b, c, d, e"));
    }

    @DisplayName("broupingBy 이요하여 그룹핑 하기")
    @Test
    public void grouping() throws Exception {
        //when
        Map<SportType, List<Player>> collect = players.stream()
                .collect(Collectors.groupingBy(Player::getSportType));

        System.out.println(collect);
        /**
         *{SOCCER=[Player(name=a, enroll=true, height=10, type=SOCCER), Player(name=c, enroll=true, height=20, type=SOCCER)],
         * BASKETBALL=[Player(name=e, enroll=true, height=30, type=BASKETBALL)],
         * BASEBALL=[Player(name=b, enroll=false, height=40, type=BASEBALL), Player(name=d, enroll=false, height=50, type=BASEBALL)]}
         */
    }

    @DisplayName("롬복 이용하여 그룹핑")
    @Test
    public void grouping_lombok() throws Exception {
        //when
        Map<GroupType, List<Player>> collect = players.stream()
                .collect(Collectors.groupingBy(player -> {
                    if (player.getHeight() < 20) return GroupType.C;
                    else if (player.getHeight() < 40) return GroupType.B;
                    else return GroupType.A;
                }));

        System.out.println(collect);
        /**
         * {C=[Player(name=a, enroll=true, height=10, sportType=SOCCER)],
         * B=[Player(name=c, enroll=true, height=20, sportType=SOCCER), Player(name=e, enroll=true, height=30, sportType=BASKETBALL)],
         * A=[Player(name=b, enroll=false, height=40, sportType=BASEBALL), Player(name=d, enroll=false, height=50, sportType=BASEBALL)]}
         */
    }

    @DisplayName("특정 그룹에서 특정 조건을 만족하는 요소 구하기")
    @Test
    public void sub_grouping() throws Exception {
        //given
        Comparator<Player> comparing = Comparator.comparing(Player::getHeight);

        //when
        Map<SportType, Optional<Player>> collect = players.stream() //각 종목에서 가장 키가 큰 선수를 추출
                .collect(Collectors.groupingBy(Player::getSportType,
                        Collectors.maxBy(comparing)));

        System.out.println(collect);
        /**
         * {{SOCCER=Optional[Player(name=c, enroll=true, height=20, sportType=SOCCER)],
         * BASEBALL=Optional[Player(name=d, enroll=false, height=50, sportType=BASEBALL)],
         * BASKETBALL=Optional[Player(name=e, enroll=true, height=30, sportType=BASKETBALL)]}
         */
    }

    @DisplayName("collector의 결과를 다른 형식으로 변환")
    @Test
    public void tss() throws Exception {
        //given
        Comparator<Player> comparing = Comparator.comparing(Player::getHeight);

        //when
        Map<SportType, Player> collect = players.stream() //각 종목에서 가장 키가 큰 선수를 추출
                .collect(Collectors.groupingBy(
                        Player::getSportType,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Player::getHeight)),
                                Optional::get)));

        System.out.println(collect);
        /**
         * {SOCCER=Player(name=c, enroll=true, height=20, sportType=SOCCER),
         * BASEBALL=Player(name=d, enroll=false, height=50, sportType=BASEBALL),
         * BASKETBALL=Player(name=e, enroll=true, height=30, sportType=BASKETBALL)}
         */
    }

    @DisplayName("분할 함수를 이용한 그룹화")
    @Test
    public void partitioning() throws Exception {
        //when
        Map<Boolean, List<Player>> collect = players.stream()
                .collect(Collectors.partitioningBy(Player::isEnroll));

        System.out.println(collect);
        /**
         * {false=[Player(name=b, enroll=false, height=40, sportType=BASEBALL), Player(name=d, enroll=false, height=50, sportType=BASEBALL)],
         * true=[Player(name=a, enroll=true, height=10, sportType=SOCCER), Player(name=c, enroll=true, height=20, sportType=SOCCER), Player(name=e, enroll=true, height=30, sportType=BASKETBALL)]}
         */
     }
}