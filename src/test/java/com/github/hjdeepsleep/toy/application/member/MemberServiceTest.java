package com.github.hjdeepsleep.toy.application.member;

import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.member.MemberJpqlRepository;
import com.github.hjdeepsleep.toy.domain.mamber.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberJpqlRepository memberJpqlRepository;

    @DisplayName("회원가입")
    @Test
    @Rollback(value = false)
    public void join() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        Assertions.assertEquals(member, memberJpqlRepository.findOne(savedId));
    }

    @DisplayName("회원 중복 검사")
    @Test
    public void validate_duplicate() throws Exception {
        //given
        Member member1 = new Member();
        member1.setUsername("kim");

        Member member2 = new Member();
        member2.setUsername("kim");

        //when
        memberService.join(member1);
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }
}