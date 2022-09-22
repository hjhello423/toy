package com.my.discount;

import com.my.member.Grade;
import com.my.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private static final int discountFixAccount = 1_000;

    @Override
    public int discount(Member member, int price) {
        if (Grade.VIP.equals(member.getGrade())) {
            return discountFixAccount;
        }
        return 0;
    }
}
