package com.my.discount;

import com.my.member.Member;

public interface DiscountPolicy {

    int discount(Member member, int price);
}
