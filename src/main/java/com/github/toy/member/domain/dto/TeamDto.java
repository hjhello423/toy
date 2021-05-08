package com.github.toy.member.domain.dto;

import com.github.toy.member.domain.Member;
import com.github.toy.member.domain.Team;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TeamDto {

    private Team team;
    private Member member;

    @QueryProjection
    public TeamDto(Team team, Member members) {
        this.team = team;
        this.member = members;
    }

}
