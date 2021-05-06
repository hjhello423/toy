package com.github.hjdeepsleep.toy.mordern_java;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Player {

    private final String name;
    private final boolean enroll;
    private final int height;
    private final SportType sportType;

}
