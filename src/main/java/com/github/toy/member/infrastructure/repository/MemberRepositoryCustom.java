package com.github.toy.member.infrastructure.repository;

import com.github.toy.member.domain.dto.MemberSearchCondition;
import com.github.toy.member.domain.dto.MemberTeamDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCondition condition);

    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);

    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);

    Page<MemberTeamDto> searchPageComplexAdvanced(MemberSearchCondition condition, Pageable pageable);

}
