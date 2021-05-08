package com.github.toy.domain.member;

import com.github.toy.domain.mamber.Member;
import com.github.toy.domain.mamber.QMember;
import com.github.toy.domain.mamber.Team;
import com.github.toy.domain.mamber.dto.MemberDto;
import com.github.toy.domain.mamber.dto.QMemberDto;
import com.github.toy.domain.mamber.dto.UserDto;
import com.github.toy.domain.mamber.QTeam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
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

//        em.flush();
//        em.clear();
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
                .selectFrom(QMember.member)
                .from(QMember.member)
                .where(QMember.member.username.eq("member1"))
                .fetchOne();

        assertTrue(findMember.getUsername().equals("member1"));
    }

    @DisplayName("조건 쿼리 하기")
    @Test
    public void search() throws Exception {
        //given
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1")
                        .and(QMember.member.age.eq(10)))
                .fetchOne();

        //then
        assertTrue(findMember.getUsername().equals("member1"));
    }

    @Test
    public void search_and_param() throws Exception {
        //given
        Member findMember = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"), // ',' 이용하여 전달 해도 and로 묶어준다
                        (QMember.member.age.eq(10)))
                .fetchOne();

        //then
        assertTrue(findMember.getUsername().equals("member1"));
    }

    @DisplayName("결과 조회 하기")
    @Test
    public void get_result() throws Exception {
        List<Member> fetch = queryFactory
                .selectFrom(QMember.member)
                .fetch(); //리스트 반환

        Member fetchOne = queryFactory
                .selectFrom(QMember.member)
                .fetchOne(); //단 건 조회

        Member fetchFirst = queryFactory
                .selectFrom(QMember.member)
                .fetchFirst(); // .limit(1).fetchOne()

        QueryResults<Member> results = queryFactory
                .selectFrom(QMember.member)
                .fetchResults();

        results.getTotal(); //count 쿼리 실행
        results.getLimit();
        List<Member> results1 = results.getResults(); //데이터 조회 쿼리 실행

        queryFactory
                .selectFrom(QMember.member)
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
                .selectFrom(QMember.member)
                .where(QMember.member.age.eq(100))
                .orderBy(QMember.member.age.desc(), QMember.member.username.asc().nullsLast())
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
                .selectFrom(QMember.member)
                .orderBy(QMember.member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertEquals(result.size(), 2);
    }

    @DisplayName("페이징 - 전체 조회 수")
    @Test
    public void paging2() throws Exception {
        QueryResults<Member> result = queryFactory
                .selectFrom(QMember.member)
                .orderBy(QMember.member.username.desc())
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
                        QMember.member.count(),
                        QMember.member.age.sum(),
                        QMember.member.age.avg(),
                        QMember.member.age.max(),
                        QMember.member.age.min()
                )
                .from(QMember.member)
                .fetch();

        Tuple tuple = result.get(0);
        org.junit.jupiter.api.Assertions.assertEquals(tuple.get(QMember.member.count()), 4);
        org.junit.jupiter.api.Assertions.assertEquals(tuple.get(QMember.member.age.sum()), 100);
        org.junit.jupiter.api.Assertions.assertEquals(tuple.get(QMember.member.age.avg()), 25);
        org.junit.jupiter.api.Assertions.assertEquals(tuple.get(QMember.member.age.max()), 40);
        org.junit.jupiter.api.Assertions.assertEquals(tuple.get(QMember.member.age.min()), 10);
    }

    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라
     */
    @DisplayName("groupby")
    @Test
    public void groupby() throws Exception {
        List<Tuple> result = queryFactory
                .select(QTeam.team.name, QMember.member.age.avg())
                .from(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .groupBy(QTeam.team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertEquals(teamA.get(QTeam.team.name), "teamA");
        org.junit.jupiter.api.Assertions.assertEquals(teamA.get(QMember.member.age.avg()), 15);
        assertEquals(teamB.get(QTeam.team.name), "teamB");
        org.junit.jupiter.api.Assertions.assertEquals(teamB.get(QMember.member.age.avg()), 35);
    }

    /**
     * 팀 A에 소속된 모든 회원 찾기
     */
    @DisplayName("join")
    @Test
    public void join() throws Exception {
        List<Member> result = queryFactory
                .selectFrom(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .where(QTeam.team.name.eq("teamA"))
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
                .select(QMember.member)
                .from(QMember.member, QTeam.team) // cross join 됨
                .where(QMember.member.username.eq(QTeam.team.name))
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
                .select(QMember.member, QTeam.team)
                .from(QMember.member)
                .leftJoin(QMember.member.team, QTeam.team)
                .on(QTeam.team.name.eq("teamA"))
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
                .select(QMember.member, QTeam.team)
                .from(QMember.member)
                .leftJoin(QTeam.team)
                .on(QMember.member.username.eq(QTeam.team.name))
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
                .selectFrom(QMember.member)
                .where(QMember.member.username.eq("member1"))
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
                .selectFrom(QMember.member)
                .join(QMember.member.team, QTeam.team).fetchJoin()
                .where(QMember.member.username.eq("member1"))
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
                .selectFrom(QMember.member)
                .where(QMember.member.age.eq(
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
                .selectFrom(QMember.member)
                .where(QMember.member.age.goe(
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
                .selectFrom(QMember.member)
                .where(QMember.member.age.in(
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
                .select(QMember.member.username,
                        JPAExpressions
                                .select(memberSub.age.avg())
                                .from(memberSub))
                .from(QMember.member)
                .fetch();

        //then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }


    @DisplayName("case문 사용하기")
    @Test
    public void basic_case() throws Exception {
        //when
        List<String> result = queryFactory
                .select(QMember.member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(QMember.member)
                .fetch();

        //then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @DisplayName("복잡한 case - CaseBuilder")
    @Test
    public void complex_case() throws Exception {
        //when
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(QMember.member.age.between(0, 20)).then("0~10살")
                        .when(QMember.member.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(QMember.member)
                .fetch();

        //then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @DisplayName("상수")
    @Test
    public void constant() throws Exception {
        //when
        List<Tuple> result = queryFactory
                .select(QMember.member.username, Expressions.constant("A"))
                .from(QMember.member)
                .fetch();

        //then
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * {userName}_{age}
     *
     * @throws Exception
     */
    @DisplayName("문자 더하기")
    @Test
    public void concat() throws Exception {
        //when
        List<String> result = queryFactory
                .select(QMember.member.username.concat("_").concat(QMember.member.age.stringValue())) //타입을 String으로맞춰줘야함, enum에도 자주 사용
                .from(QMember.member)
                .fetch();

        //then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @DisplayName("projection 대상이 1개")
    @Test
    public void projection() throws Exception {
        //when
        List<String> result = queryFactory
                .select(QMember.member.username)
                .from(QMember.member)
                .fetch();
        //then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @DisplayName("projection 대상이 여러개")
    @Test
    public void projection_tuple() throws Exception {
        //when
        List<Tuple> result = queryFactory
                .select(QMember.member.username, QMember.member.age)
                .from(QMember.member)
                .fetch();
        //then
        for (Tuple tuple : result) {
            String userName = tuple.get(QMember.member.username);
            Integer age = tuple.get(QMember.member.age);
            System.out.println(userName + " = " + age);
        }
    }

    @DisplayName("DTO로 프로젝션 하기 - jpql new operation")
    @Test
    public void dto_projection() throws Exception {
        //given
        List<MemberDto> result = em.createQuery("select new com.github.hjdeepsleep.toy.domain.mamber.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                .getResultList();

        //then
        for (MemberDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("DTO로 프로젝션 하기 - querydsl setter이용")
    @Test
    public void querydsl_setter() throws Exception {
        //given
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class, //MemberDto에 setter()가 선언되어 있어야 동작 한다.
                        QMember.member.username,
                        QMember.member.age))
                .from(QMember.member)
                .fetch();

        //then
        for (MemberDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("DTO로 프로젝션 하기 - querydsl field 이용")
    @Test
    public void querydsl_field() throws Exception {
        //given
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class,
                        QMember.member.username,
                        QMember.member.age))
                .from(QMember.member)
                .fetch();

        //then
        for (MemberDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("DTO로 프로젝션 하기 - querydsl 생성자 이용")
    @Test
    public void querydsl_constructor() throws Exception {
        //given
        List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class,
                        QMember.member.username,
                        QMember.member.age))
                .from(QMember.member)
                .fetch();

        //then
        for (MemberDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("DTO로 프로젝션 하기 - userDot 이용")
    @Test
    public void use_userDto() throws Exception {
        //given
        QMember memberSub = new QMember("memberSub");

        //when
        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        QMember.member.username.as("name"), //필드 명 name으로 매핑
                        ExpressionUtils.as(JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub), "age")
                ))
                .from(QMember.member)
                .fetch();

        //then
        for (UserDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("DTO로 프로젝션 하기 - userDot, 생성자 이용")
    @Test
    public void use_userDto_constructor() throws Exception {
        //given
        List<UserDto> result = queryFactory
                .select(Projections.constructor(UserDto.class,
                        QMember.member.username, //생성자와 타입이 일치 해야 함
                        QMember.member.age))
                .from(QMember.member)
                .fetch();

        //then
        for (UserDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("@QueryProjection")
    @Test
    public void queryProjection_annotation() throws Exception {
        //given
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(QMember.member.username, QMember.member.age))
                .from(QMember.member)
                .fetch();
        //then
        for (MemberDto dto : result) {
            System.out.println("dto = " + dto);
        }
    }

    @DisplayName("동적 쿼리 - BooleanBuilder")
    @Test
    public void dynamicQuery_booleanBuilder() throws Exception {
        //given
        String usernameParam = "member1";
        Integer ageParam = null;

        //when
        List<Member> result = searchMember1(usernameParam, ageParam);

        //then
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameParam, Integer ageParam) {

        BooleanBuilder builder = new BooleanBuilder();

        if (usernameParam != null) {
            builder.and(QMember.member.username.eq(usernameParam));
        }

        if (ageParam != null) {
            builder.and(QMember.member.age.eq(ageParam));
        }
        return queryFactory
                .selectFrom(QMember.member)
                .where(builder)
                .fetch();
    }

    @DisplayName("동적 쿼리 - where절에서")
    @Test
    public void dynamicQuery_whereParam() throws Exception {
        //given
        String usernameParam = "member1";
        Integer ageParam = 10;

        //when
        List<Member> result = searchMember2(usernameParam, ageParam);

        //then
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameParam, Integer ageParam) {
        return queryFactory
                .selectFrom(QMember.member)
//                .where(usernameEq(usernameParam), ageEq(ageParam))
                .where(allEq(usernameParam, ageParam))
                .fetch();
    }

    private BooleanExpression usernameEq(String usernameParam) {
        if (usernameParam == null) {
            return null;
        }
        return QMember.member.username.eq(usernameParam);
    }

    private BooleanExpression ageEq(Integer ageParam) {
        return ageParam == null ? null : QMember.member.age.eq(ageParam);
    }

    private BooleanExpression allEq(String usernameParam, Integer ageParam) {
        return usernameEq(usernameParam).and(ageEq(ageParam));
    }

    @DisplayName("bulk")
    @Test
//    @Commit
    public void bulkUpdate() throws Exception {
        //member1 = 10 -> 비회원
        //member2 = 20 -> 비회원
        //member3 = 30 -> 유지
        //member4 = 40 -> 유지

        //when
        long count = queryFactory
                .update(QMember.member)
                .set(QMember.member.username, "비회원")
                .where(QMember.member.age.lt(28))
                .execute();

        //bulk 연산 실행시 persistence context와 데이터 불일치가 일어난다
        //bulk 연산은 persistence context를 무시하고 insert 하기 때문이다.

        em.flush(); //bulk연산 이후에는 PC를 초기화 해주는것이 좋다
        em.clear();

        //then
        List<Member> result = queryFactory
                .selectFrom(QMember.member)
                .fetch();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @DisplayName("bulk 연산 - 더하기, 곱하기")
    @Test
    public void bulkAdd() throws Exception {
        //when
        long count = queryFactory
                .update(QMember.member)
                .set(QMember.member.age, QMember.member.age.add(1))
//                .set(member.age, member.age.multiply(2))
                .execute();
    }

    @DisplayName("bulk 연산 - delete")
    @Test
    public void bulkDelete() throws Exception {
        //when
        long count = queryFactory
                .delete(QMember.member)
                .where(QMember.member.age.gt(18))
                .execute();
    }

    @DisplayName("sql function - replace 사용해 보기")
    @Test
    public void sqlFunction() throws Exception {
        //when
        List<String> result = queryFactory
                .select(
                        Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                                QMember.member.username, "member", "M"))
                .from(QMember.member)
                .fetch();

        //then
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction2() throws Exception {
        //when
        List<String> result = queryFactory
                .select(QMember.member.username)
                .from(QMember.member)
//                .where(member.username.eq(
//                        Expressions.stringTemplate("function('lower', {0})", member.username)))
                .where(QMember.member.username.eq(QMember.member.username.lower()))
                .fetch();

        //then
        for (String s : result) {
            System.out.println("s = " + s);
        }

    }

}
