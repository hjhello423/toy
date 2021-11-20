package com.my.studydesignpattern.chapter5.practice.practice_3;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;

public class Member {

    private String name;
    @Getter
    private BigDecimal accumulateAmount;

    public void rent(List<Book> books) {
        BigDecimal amount = books.stream()
            .map(Book::getPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        accumulateAmount.add(amount);
    }

    public void rent(BigDecimal price) {
        accumulateAmount.add(price);
    }

}
