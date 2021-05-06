package com.github.hjdeepsleep.toy.domain.mamber.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int age;

    @QueryProjection //QMemberDto 생성
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }

}
