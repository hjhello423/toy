package com.my.config;

import com.my.discount.FixDiscountPolicy;
import com.my.member.MemberService;
import com.my.member.MemberServiceImpl;
import com.my.member.MemoryMemberRepository;
import com.my.order.OrderService;
import com.my.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

}
