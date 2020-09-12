package com.github.hjdeepsleep.toy.mordern_java;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Player {
    private final String name;
    private final boolean enroll;
    private final int height;
    private final Type type;
}
