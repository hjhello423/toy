package com.my.studydesignpattern.chapter11.practice6;

import lombok.Getter;

@Getter
public class Customer {

    private String name;
    private int point;

    public Customer(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public boolean isOverLimitPoint(int limit) {
        return point >=limit;
    }

}
