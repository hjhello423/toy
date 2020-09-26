package com.github.hjdeepsleep.toy.repository;

import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.MemberJpaRepository;
import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.mamber.Team;
import com.github.hjdeepsleep.toy.domain.mamber.dto.MemberSearchCondition;
import com.github.hjdeepsleep.toy.domain.mamber.dto.MemberTeamDto;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basic_jpql_test() throws Exception {
        //given
        Member member = new Member("member1", 10);

        //when
        Long saveId = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.findById(saveId)
                .orElseThrow(() -> new NotFoundException("not found user"));

        //then
        assertThat(findMember).isEqualTo(member);

        List<Member> findAll = memberJpaRepository.findAll();
        assertThat(findAll).containsExactly(member);

        List<Member> findByName = memberJpaRepository.findByUsername("member1");
        assertThat(findByName).containsExactly(member);
    }

    @Test
    public void basic_querydsl_test() throws Exception {
        //given
        Member member = new Member("member1", 10);

        //when
        memberJpaRepository.save(member);

        //then
        List<Member> result1 = memberJpaRepository.findAll_querydsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername_querydsl("member1");
        assertThat(result2).containsExactly(member);
    }


    @Test
    public void searchTest() throws Exception {
        //given
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

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        //when
//        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition); //builder이용한 동적 쿼리 생성
        List<MemberTeamDto> result = memberJpaRepository.search(condition); //where절 이용한ㄴ 동적 쿼리 생성

        //then
        assertThat(result).extracting("username").containsExactly("member4");
    }

}