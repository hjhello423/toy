package com.github.hjdeepsleep.toy;

import com.github.hjdeepsleep.toy.domain.Member;
import com.github.hjdeepsleep.toy.domain.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.github.hjdeepsleep.toy.domain.QMember.member;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class BasicTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @DisplayName("JPQL로 조회")
    @Test
    public void startJPQL() throws Exception {
        String qlString = "select m from Member m " +
                "where m.username = :username";

        Member findMember = em.createQuery(qlString, Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertTrue(findMember.getUsername().equals("member1"));
    }

    @DisplayName("querydsl 이용")
    @Test
    public void startQuerydsl() throws Exception {
        Member findMember = queryFactory
                .selectFrom(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertTrue(findMember.getUsername().equals("member1"));
    }

    @DisplayName("조건 쿼리 하기")
    @Test
    public void search() throws Exception {
        //given
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1")
                        .and(member.age.eq(10)))
                .fetchOne();

        //then
        assertTrue(findMember.getUsername().equals("member1"));
    }

    @Test
    public void search_and_param() throws Exception {
        //given
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"), // ',' 이용하여 전달 해도 and로 묶어준다
                        (member.age.eq(10)))
                .fetchOne();

        //then
        assertTrue(findMember.getUsername().equals("member1"));
    }

    @DisplayName("결과 조회 하기")
    @Test
    public void get_result() throws Exception {
        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();

        Member fetchOne = queryFactory
                .selectFrom(member)
                .fetchOne();

        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst(); // .limit(1).fetchOne()

        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();

        results.getTotal(); //count 쿼리 실행
        results.getLimit();
        List<Member> results1 = results.getResults(); //데이터 조회 쿼리 실행
    }
}
