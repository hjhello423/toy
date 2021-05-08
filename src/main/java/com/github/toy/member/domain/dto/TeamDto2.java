package com.github.toy.member.domain.dto;

import com.github.toy.member.domain.Member;
import com.github.toy.member.domain.Team;
import com.querydsl.core.annotations.QueryProjection;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

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
