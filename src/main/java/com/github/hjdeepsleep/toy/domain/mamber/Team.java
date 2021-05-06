package com.github.hjdeepsleep.toy.domain.mamber;

import static lombok.AccessLevel.PROTECTED;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "name", "rank", "members"})
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer rank;

    @Getter
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }

    public Team(String name, Integer rank) {
        this(name);
        this.rank = rank;
    }

}
