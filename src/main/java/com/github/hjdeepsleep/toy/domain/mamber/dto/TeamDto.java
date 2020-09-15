package com.github.hjdeepsleep.toy.domain.mamber.dto;

import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.mamber.Team;
import com.querydsl.core.annotations.QueryProjection;
import lombok.ToString;

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
