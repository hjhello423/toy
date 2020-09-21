package com.github.hjdeepsleep.toy.repository;

import com.github.hjdeepsleep.toy.domain.mamber.dto.MemberSearchCondition;
import com.github.hjdeepsleep.toy.domain.mamber.dto.MemberTeamDto;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);
}
