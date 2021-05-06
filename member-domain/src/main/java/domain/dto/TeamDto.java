package domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import domain.Member;
import domain.Team;
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
