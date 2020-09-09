package com.github.hjdeepsleep.toy;

import com.github.hjdeepsleep.toy.domain.Member;
import com.github.hjdeepsleep.toy.domain.QMember;
import com.github.hjdeepsleep.toy.domain.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.github.hjdeepsleep.toy.domain.QMember.member;
import static com.github.hjdeepsleep.toy.domain.QTeam.team;
import static org.junit.jupiter.api.Assertions.*;

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
                .fetch(); //리스트 반환

        Member fetchOne = queryFactory
                .selectFrom(member)
                .fetchOne(); //단 건 조회

        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst(); // .limit(1).fetchOne()

        QueryResults<Member> results = queryFactory
                .selectFrom(member)
                .fetchResults();

        results.getTotal(); //count 쿼리 실행
        results.getLimit();
        List<Member> results1 = results.getResults(); //데이터 조회 쿼리 실행

        queryFactory
                .selectFrom(member)
                .fetchCount(); //count 조회
    }

    /**
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단, 2에서 회원 이름이 없으면 마지막에 출력(null last)
     */
    @DisplayName("정렬")
    @Test
    public void sort() throws Exception {
        em.persist(new Member(null, 100));
        em.persist(new Member("member5", 100));
        em.persist(new Member("member6", 100));

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);

        assertTrue(member5.getUsername().equals("member5"));
        assertTrue(member6.getUsername().equals("member6"));
        assertNull(memberNull.getUsername());
    }

    @DisplayName("페이징")
    @Test
    public void paging1() throws Exception {
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertEquals(result.size(), 2);
    }

    @DisplayName("페이징 - 전체 조회 수")
    @Test
    public void paging2() throws Exception {
        QueryResults<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertEquals(result.getTotal(), 4);
        assertEquals(result.getLimit(), 2);
        assertEquals(result.getOffset(), 1);
        assertEquals(result.getResults().size(), 2);
    }

    @DisplayName("집함 함수")
    @Test
    public void aggregation() throws Exception {
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertEquals(tuple.get(member.count()), 4);
        assertEquals(tuple.get(member.age.sum()), 100);
        assertEquals(tuple.get(member.age.avg()), 25);
        assertEquals(tuple.get(member.age.max()), 40);
        assertEquals(tuple.get(member.age.min()), 10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라
     */
    @DisplayName("groupby")
    @Test
    public void groupby() throws Exception {
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertEquals(teamA.get(team.name), "teamA");
        assertEquals(teamA.get(member.age.avg()), 15);
        assertEquals(teamB.get(team.name), "teamB");
        assertEquals(teamB.get(member.age.avg()), 35);
    }

    /**
     * 팀 A에 소속된 모든 회원 찾기
     */
    @DisplayName("join")
    @Test
    public void join() throws Exception {
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();
    }

    /**
     * 세타 조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     *
     * @throws Exception
     */
    @DisplayName("연관 관계가 없는 테이블의 조인")
    @Test
    public void theta_join() throws Exception {
        //given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        //when
        List<Member> result = queryFactory
                .select(member)
                .from(member, team) // cross join 됨
                .where(member.username.eq(team.name))
                .fetch();
    }

    /**
     * 회원과 팀을 join하면서, 팀 이름이 teamA인 팀만 join
     * 회원은 모두 조회
     */
    @Test
    public void joi_on_filtering() throws Exception {
        //given

        //when
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        //then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * 연관 관계가 없는 엔티티 외부 조인
     * 회원의 이름이 팀 이름과 같은 대상 외부 조인
     */
    @Test
    public void joi_on_no_relation() throws Exception {
        //given
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        //when
        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team)
                .on(member.username.eq(team.name))
                .fetch();

        //then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @DisplayName("fetch join - lazy 로딩 객체 확인")
    @Test
    public void fetch_join() throws Exception {
        //given
        em.flush();//fetch join 테스트 시에는 영속성 컨텍스트 정리하는게 좋음
        em.clear();

        //when
        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        //then
        assertFalse(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam()));
    }

    @DisplayName("fetch join - 로딩 확인")
    @Test
    public void fetch_join_use() throws Exception {
        //given
        em.flush();//fetch join 테스트 시에는 영속성 컨텍스트 정리하는게 좋음
        em.clear();

        //when
        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        //then
        assertTrue(emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam()));
    }

    /**
     * 나이가 가장 많은회원 조회
     */
    @DisplayName("서브쿼리 사용해보기")
    @Test
    public void subQuery() throws Exception {
        //given
        QMember memberSub = new QMember("memberSub");

        //when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        //then
        Assertions.assertThat(result)
                .extracting("age")
                .containsExactly(40);
    }

    /**
     * 나이가 평균 이상인 회원 조회
     */
    @DisplayName("서브쿼리 사용해보기")
    @Test
    public void subQuery_avg() throws Exception {
        //given
        QMember memberSub = new QMember("memberSub");

        //when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        //then
        Assertions.assertThat(result)
                .extracting("age")
                .containsExactly(30, 40);
    }

    @DisplayName("서브쿼리 사용해보기")
    @Test
    public void subQuery_in() throws Exception {
        //given
        QMember memberSub = new QMember("memberSub");

        //when
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        JPAExpressions
                                .select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        //then
        Assertions.assertThat(result)
                .extracting("age")
                .containsExactly(20, 30, 40);
    }

    @DisplayName("select절에 subQuery 사용해보기")
    @Test
    public void subQuery_select() throws Exception {
        //given
        QMember memberSub = new QMember("memberSub");

        //when
        List<Tuple> result = queryFactory
                .select(member.username,
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();

        //then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }
}
