package com.my.discount;

import com.my.member.Grade;
import com.my.member.Member;

public class RateDiscountPolicy implements DiscountPolicy {

    private static final int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (Grade.VIP.equals(member.getGrade())) {
            return price * discountPercent / 100;
        }
        return 0;
    }
}
