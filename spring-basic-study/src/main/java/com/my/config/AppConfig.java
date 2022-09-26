package com.my.config;

import com.my.discount.DiscountPolicy;
import com.my.discount.RateDiscountPolicy;
import com.my.member.MemberService;
import com.my.member.MemberServiceImpl;
import com.my.member.MemoryMemberRepository;
import com.my.order.OrderService;
import com.my.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), getDiscountPolicy());
    }

    @Bean
    public DiscountPolicy getDiscountPolicy() {
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

}
