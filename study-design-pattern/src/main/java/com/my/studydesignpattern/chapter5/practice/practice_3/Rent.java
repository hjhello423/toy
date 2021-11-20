package com.my.studydesignpattern.chapter5.practice.practice_3;

import static lombok.AccessLevel.PRIVATE;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class Rent {

    private static final BigDecimal DISCOUNT_STANDARD_PRICE = new BigDecimal(1000);
    private static final int YEAR = 365;

    private Member member;
    private Book book;

    public static Rent of(Member member, Book book) {
        Rent result = new Rent();

        result.member = member;
        result.book = book;
        result.calc();

        return result;
    }

    private void calc() {
        PriceStrategy discountPolicy = decideDiscountPolicy();
        BigDecimal rentAmount = discountPolicy.calc(book.getPrice());
        member.rent(rentAmount);
    }

    private PriceStrategy decideDiscountPolicy() {
        if (member.getAccumulateAmount().compareTo(DISCOUNT_STANDARD_PRICE) >= 0) {
            return new MemberDiscountPolicy();
        }

        Duration between = Duration.between(book.getPublicationDate(), LocalDateTime.now());
        if (between.toDays() > YEAR) {
            return new BookDiscountPolicy();
        }

        return new NonDiscountPolicy();
    }

}
