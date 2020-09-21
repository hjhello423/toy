package com.github.hjdeepsleep.toy.repository;

import com.github.hjdeepsleep.toy.domain.mamber.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    List<Member> findByUsername(String username);

}
