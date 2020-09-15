package com.github.hjdeepsleep.toy.domain.member;

import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.mamber.Team;
import com.github.hjdeepsleep.toy.domain.mamber.dto.QTeamDto;
import com.github.hjdeepsleep.toy.domain.mamber.dto.TeamDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.github.hjdeepsleep.toy.domain.mamber.QMember.member;
import static com.github.hjdeepsleep.toy.domain.mamber.QTeam.team;

@SpringBootTest
@Transactional
public class JoinTest2 {

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

        em.flush();
        em.clear();
    }

    /**
     * team만 조회 - team->member @OneToMany(lazy)
     *
     jpql/sql
     select team
     from Team team
     where team.rank <= 2

     select
         team0_.id as id1_2_,
         team0_.name as name2_2_,
         team0_.rank as rank3_2_
     from team team0_
     where team0_.rank<=2
     */
    @Test
    public void test0() throws Exception {
        queryFactory = new JPAQueryFactory(em);

        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .where(team.rank.loe(2))
                .fetch();

        for (Team team : result) {
            System.out.println(team);
        }
        /**
         * team.members 사용 할때마다 아래의 쿼리 실행
         select
             members0_.team_id as team_id4_1_0_,
             members0_.member_id as member_i1_1_0_,
             members0_.member_id as member_i1_1_1_,
             members0_.age as age2_1_1_,
             members0_.team_id as team_id4_1_1_,
             members0_.username as username3_1_1_
         from member members0_
         where members0_.team_id=?
         */
    }

    /**
     * 일반 join
     *

     jpql/sql
     select team
     from Team team
     inner join team.members as member1
     where team.rank <= 2 and member1.age = 10

     select
        team0_.id as id1_2_,
        team0_.name as name2_2_,
        team0_.rank as rank3_2_
     from
        team team0_
     inner join
        member members1_
            on team0_.id=members1_.team_id
     where
        team0_.rank<=2 and members1_.age=10
     */
    @Test
    public void test1() throws Exception {
        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .join(team.members, member)
                .where(team.rank.loe(2))
                .where(member.age.eq(10))
                .fetch();

        for (Team team : result) {
            System.out.println(team);
        }
        /**
        결과
        test0과 동일하게 team.members 사용 할 때 마다 select 쿼리 실행

        application -> age가 10이 아닌것도 조회
        Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)])
        Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)])

         db -> team테이블만 조회
         ID1_2_  	NAME2_2_  	RANK3_2_
         1	        team	        1
         2	        team2	        2
        */
    }

   /**
    * fetch join

    jpql/sql
    select team
    from Team team
    inner join fetch team.members as member1
    where team.rank <= 2

    select
        team0_.id as id1_2_0_,
        members1_.member_id as member_i1_1_1_,
        team0_.name as name2_2_0_,
        team0_.rank as rank3_2_0_,
        members1_.age as age2_1_1_,
        members1_.team_id as team_id4_1_1_,
        members1_.username as username3_1_1_,
        members1_.team_id as team_id4_1_0__,
        members1_.member_id as member_i1_1_0__
    from
        team team0_
    inner join
        member members1_
            on team0_.id=members1_.team_id
    where team0_.rank<=2
    */
    @Test
    public void test2() throws Exception {
        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .join(team.members, member)
                .fetchJoin()
                .where(team.rank.loe(2))
                .fetch();

        for (Team team : result) {
            System.out.println(team);
        }
        /**
         결과

         fetch join으로 인해 member 한번에 불러옴
         Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)])
         Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)])
         Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)])
         Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)])
         Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)])
         Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)])

         db -> team, member 같이 조회

         ID1_2_0_  	MEMBER_I1_1_1_  	NAME2_2_0_  	RANK3_2_0_  	AGE2_1_1_  	TEAM_ID4_1_1_  	USERNAME3_1_1_  	TEAM_ID4_1_0__  	MEMBER_I1_1_0__
         1	5	team	1	10	1	member1-1	1	5
         1	6	team	1	20	1	member1-2	1	6
         1	7	team	1	30	1	member1-3	1	7
         2	8	team2	2	10	2	member2-1	2	8
         2	9	team2	2	20	2	member2-2	2	9
         2	10	team2	2	30	2	member2-3	2	10
         */
    }

    /**
     * fetch join - 대상을 where 조건에 사용하기

     jpql/sql
     select team
     from Team team
     inner join fetch team.members as member1
     where team.rank <= 2 and member1.age = 10

     select
         team0_.id as id1_2_0_,
         members1_.member_id as member_i1_1_1_,
         team0_.name as name2_2_0_,
         team0_.rank as rank3_2_0_,
         members1_.age as age2_1_1_,
         members1_.team_id as team_id4_1_1_,
         members1_.username as username3_1_1_,
         members1_.team_id as team_id4_1_0__,
         members1_.member_id as member_i1_1_0__
     from
        team team0_
     inner join member members1_
        on team0_.id=members1_.team_id
     where team0_.rank<=2 and members1_.age=10
     */
    @Test
    public void test3() throws Exception {
        //when
        List<Team> result = queryFactory
                .select(team)
                .from(team)
                .join(team.members, member)
                .fetchJoin()
                .where(team.rank.loe(2))
                .where(member.age.eq(10)) //fetch join 대상을 where에
                .fetch();

        for (Team team : result) {
            System.out.println(team);
        }
        /**
         결과
         Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10)])
         Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10)])

         db -> team, member 같이 조회
         ID1_2_0_  	MEMBER_I1_1_1_  	NAME2_2_0_  	RANK3_2_0_  	AGE2_1_1_  	TEAM_ID4_1_1_  	USERNAME3_1_1_  	TEAM_ID4_1_0__  	MEMBER_I1_1_0__
         1	5	team	1	10	1	member1-1	1	5
         2	8	team2	2	10	2	member2-1	2	8
         */
    }

    /**
     * projection 이용 - team, member
     *
     jpql/sql
     select team, member1
     from Team team
     inner join team.members as member1
     where team.rank <= 2 and member1.age = 10

    select
        team0_.id as id1_2_0_,
        members1_.member_id as member_i1_1_1_,
        team0_.name as name2_2_0_,
        team0_.rank as rank3_2_0_,
        members1_.age as age2_1_1_,
        members1_.team_id as team_id4_1_1_,
        members1_.username as username3_1_1_
    from
        team team0_
    inner join
        member members1_
            on team0_.id=members1_.team_id
    where
        team0_.rank<=? and members1_.age=?
     */
    @Test
    public void test4() throws Exception {
        ///when
        List<Tuple> result = queryFactory
                .select(team, member)
                .from(team)
                .join(team.members, member)
                .where(team.rank.loe(2))
                .where(member.age.eq(10))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
        /**
         결과
         team.members 사용할때마다 select 쿼리 실행(test0과 같은 상황)

         application -> age가 10인 member 포함, team에는 여전히 age!=10인 데이터 존재
         [Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)]), Member(id=5, username=member1-1, age=10)]
         [Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)]), Member(id=8, username=member2-1, age=10)]

         db -> team, member 같이 조회
         ID1_2_0_  	MEMBER_I1_1_1_  	NAME2_2_0_  	RANK3_2_0_  	AGE2_1_1_  	TEAM_ID4_1_1_  	USERNAME3_1_1_  	TEAM_ID4_1_0__  	MEMBER_I1_1_0__
         1	            5	            team1	        1	            10	            1	        member1-1	        1               	5
         2	            8	            team2	        2	            10	            2	        member2-1	        2	                8
         */
    }

    /**
     * 결과를 한번에 dto로 받아보기
     */
    @Test
    public void test5() throws Exception {
        //when
        List<TeamDto> result = queryFactory
                .select(new QTeamDto(team, member))
                .from(team)
                .leftJoin(team.members, member)
                .on(member.age.goe(20))
                .where(team.rank.loe(2))
                .fetch();

        for (TeamDto dto : result) {
            System.out.println(dto);
        }
        /**
         * 결과
         TeamDto(team=Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)]), member=Member(id=6, username=member1-2, age=20))
         TeamDto(team=Team(id=1, name=team1, rank=1, members=[Member(id=5, username=member1-1, age=10), Member(id=6, username=member1-2, age=20), Member(id=7, username=member1-3, age=30)]), member=Member(id=7, username=member1-3, age=30))

         TeamDto(team=Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)]), member=Member(id=9, username=member2-2, age=20))
         TeamDto(team=Team(id=2, name=team2, rank=2, members=[Member(id=8, username=member2-1, age=10), Member(id=9, username=member2-2, age=20), Member(id=10, username=member2-3, age=30)]), member=Member(id=10, username=member2-3, age=30))

         TeamDto(team=Team(id=4, name=team4, rank=1, members=[]), member=null)
         */
    }
}
