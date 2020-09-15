package com.github.hjdeepsleep.toy.domain.mamber.dto;

import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.mamber.Team;
import com.querydsl.core.annotations.QueryProjection;

import java.util.ArrayList;
import java.util.List;

public class TeamDto {

    private Team team;
    private List<Member> members = new ArrayList<>();

    @QueryProjection
    public TeamDto(Team team, List<Member> members) {
        this.team = team;
        this.members = members;
    }
}
