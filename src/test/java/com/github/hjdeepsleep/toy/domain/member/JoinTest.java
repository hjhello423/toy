package com.github.hjdeepsleep.toy.domain.member;

import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.mamber.Team;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static com.github.hjdeepsleep.toy.domain.mamber.QMember.member;
import static com.github.hjdeepsleep.toy.domain.mamber.QTeam.team;

@SpringBootTest
@Transactional
public class JoinTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team team1 = new Team("team1", 1);
        Team team2 = new Team("team2", 2);
        Team team3 = new Team("team3", 3);
        Team team4 = new Team("team4", 1); //팀원 없음

        em.persist(team1);
        em.persist(team2);
        em.persist(team3);
        em.persist(team4);

        Member member1_1 = new Member("member1-1", 10, team1);
        Member member1_2 = new Member("member1-2", 20, team1);
        Member member1_3 = new Member("member1-3", 30, team1);

        Member member2_1 = new Member("member2-1", 10, team2);
        Member member2_2 = new Member("member2-2", 20, team2);
        Member member2_3 = new Member("member2-3", 30, team2);

        Member member3_1 = new Member("member3_1", 10, team3);
        Member member3_2 = new Member("member3-2", 20, team3);
        Member member3_3 = new Member("member3-3", 30, team3);

        em.persist(member1_1);
        em.persist(member1_2);
        em.persist(member1_3);
        em.persist(member2_1);
        em.persist(member2_2);
        em.persist(member2_3);
        em.persist(member3_1);
        em.persist(member3_2);
        em.persist(member3_3);
    }

    /**
     * 1~2등 팀과
     * 팀에 속한 팀월을 조회
     */
    @Test
    public void test1() throws Exception {
        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .leftJoin(team.members, member)
                .where(team.rank.loe(2))
                .fetch();

        //then
        for (Team team : result) {
            System.out.println(team);
        }
    }

    /**
     * 1~2등 팀과
     * 팀에 속한 20살 이상의 팀원을 조회
     * <p>
     * 결과 : 실패
     */
    @Test
    public void test2() throws Exception {
        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .leftJoin(team.members, member)
                .on(member.age.goe(20))
                .where(team.rank.loe(2))
                .fetch();

        //then
        Assertions.assertTrue(result.get(0)
                .getMembers().size() == 2); //20살 이상인 2명이 포함 되길 기대 했지만, 10살의 팀원 까지 함께 조회 됨
    }

    /**
     * 1~2등 팀과
     * 팀에 속한 20살 이상의 팀원을 조회
     * <p>
     * fetch join 이용하여 즉시 조회 시도
     * <p>
     * 결과 : 실패
     */
    @Test
    public void test3() throws Exception {
        //when
        Assertions.assertThrows(IllegalArgumentException.class, //fetch join에서는 alias사용못함 -> on() 사용불가
                () -> {
                    List<Team> result = queryFactory
                            .select(team)
                            .from(team)
                            .leftJoin(team.members, member)
                            .fetchJoin()
                            .on(member.age.goe(20))
                            .where(team.rank.loe(2))
                            .fetch();
                });
    }

    /**
     * 1~2등 팀과
     * 팀에 속한 20살 이상의 팀원을 조회
     * <p>
     * EntityGraph 이용 하여 즉시 조회 시도
     * <p>
     * 결과 : 실패
     */
    @Test
    public void test4() throws Exception {
        //given
        EntityGraph<Team> graph = em.createEntityGraph(Team.class);
        graph.addAttributeNodes("members");

        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .leftJoin(team.members, member)
                .on(member.age.goe(20))
                .where(team.rank.loe(2))
                .setHint("javax.persistence.fetchgraph", graph)
                .fetch();

        Assertions.assertTrue(result.get(0)
                .getMembers().size() == 2);
    }

    /**
     * 1~2등 팀과
     * 팀에 속한 20살 이상의 팀원을 조회
     * <p>
     * projection 이용
     * <p>
     * 결과 : 실패
     */
    @Test
    public void test5() throws Exception {
        //when
        List<Tuple> result = queryFactory
                .select(team, member)
                .from(team)
                .leftJoin(team.members, member)
                .on(member.age.goe(20))
                .where(team.rank.loe(2))
                .fetch();


        for (Tuple tuple : result) {
            System.out.println(tuple);
        }

        System.out.println();
        System.out.println("======");
        System.out.println(result.get(0));
        System.out.println(result.get(0).get(member));
        System.out.println(result.get(0).get(0, Member.class));
        System.out.println(result.get(0).get(1, Member.class));
        System.out.println(result.get(0).get(2, Member.class));
        System.out.println(result.get(0).get(3, Member.class));
        System.out.println("======");
        System.out.println();

        Assertions.assertTrue(result.get(0)
                .get(team).getMembers().size() == 3); // team의 members에 3명의 팀원 포함됨 -> 원하는 결과 X
    }

    /**
     * 1~2등 팀과
     * 팀에 속한 20살 이상의 팀원을 조회
     * <p>
     * dto 이용
     * <p>
     * 결과 : 실패
     */
    @Test
    public void test6() throws Exception {
        //when
//        List<Tuple> result = queryFactory
//                .select(new QTeamDto(team, member)) //member를 리스트로 받을수는 없나요?
//                .from(team)
//                .leftJoin(team.members, member)
//                .on(member.age.goe(20))
//                .where(team.rank.loe(2))
//                .fetch();
    }


    /**
     * 1~2등 팀과
     * 팀에 속한 20살 이상의 팀원을 조회
     * <p>
     * native query 이용
     * <p>
     * 결과 : 실패
     */
    @Test
    public void test7() throws Exception {
        //given
        String sql = "select team.*, member.* " +
                "from team " +
                "left join member " +
                "on(team.id = member.member_id and member.age >= 20) " +
                "where team.rank >= 2 ";
        Query nativeQuery = em.createNativeQuery(sql, Team.class);

        //when
        List<Team> result = nativeQuery.getResultList();

        //then
        for (Team team : result) {
            System.out.println(team);
        }

        Assertions.assertTrue(result.get(0)
                .getMembers().size() == 2); //20살 이상인 2명이 포함 되길 기대 했지만, 10살의 팀원 까지 함께 조회 됨
    }
}
