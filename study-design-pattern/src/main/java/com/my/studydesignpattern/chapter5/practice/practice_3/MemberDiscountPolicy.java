package com.my.studydesignpattern.chapter5.practice.practice_3;

import java.math.BigDecimal;

public class MemberDiscountPolicy implements PriceStrategy {

    public static final double DISCOUNT_RATE = 0.1;

    @Override
    public BigDecimal calc(BigDecimal price) {
        return price.subtract(price.multiply(new BigDecimal(DISCOUNT_RATE)));
    }

}
