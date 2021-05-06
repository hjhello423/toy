package com.github.hjdeepsleep.toy.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("B")
//@NoArgsConstructor(access = PROTECTED)
//@AllArgsConstructor
public class Book extends Item {

    private String author;
    private String etc;
    private String isbn;

}
