package com.github.hjdeepsleep.toy.domain.study;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
//@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class Animal {

    public Animal(String name, int weight, Integer age) {
        this.name = name;
        this.weight = weight;
        this.age = age;
    }

    private String name;
    private int weight;
    private Integer age;

    public String getColor() {
        return "red";
    }

    public String setColor() {
        return "blue";
    }

}
