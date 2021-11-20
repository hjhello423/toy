package com.my.studydesignpattern.chapter5.practice.practice_3;

import java.math.BigDecimal;

public class NonDiscountPolicy implements PriceStrategy{

    @Override
    public BigDecimal calc(BigDecimal price) {
        return price;
    }

}
