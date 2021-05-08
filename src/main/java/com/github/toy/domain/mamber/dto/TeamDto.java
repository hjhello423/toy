package com.github.toy.domain.mamber.dto;

import com.github.toy.domain.mamber.Member;
import com.github.toy.domain.mamber.Team;
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
