package com.github.hjdeepsleep.toy.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static lombok.AccessLevel.PROTECTED;

@Getter @Setter
@Entity
@DiscriminatorValue("B")
//@NoArgsConstructor(access = PROTECTED)
//@AllArgsConstructor
public class Book extends Item {

    private String author;
    private String etc;
    private String isbn;

}
