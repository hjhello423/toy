package com.github.hjdeepsleep.toy.domain.mamber;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;

}
