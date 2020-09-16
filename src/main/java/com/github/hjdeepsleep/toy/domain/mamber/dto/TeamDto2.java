package com.github.hjdeepsleep.toy.domain.mamber.dto;

import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.mamber.Team;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class TeamDto2 {
    private Team team;
    private List<Member> members;

    @QueryProjection
    public TeamDto2(Team team, List<Member> members) {
        this.team = team;
        this.members = members;
    }
}
