package com.my.studydesignpattern.chapter6.practice.practice_1;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class Singleton {

    public static Singleton getInstance() {
        return SingletonClazz.INSTANCE;
    }

    private static class SingletonClazz {

        private static final Singleton INSTANCE = new Singleton();

    }

}

