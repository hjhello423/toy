package com.my;

import com.my.member.Grade;
import com.my.member.Member;
import com.my.member.MemberService;
import com.my.member.MemberServiceImpl;
import com.my.order.Order;
import com.my.order.OrderService;
import com.my.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;

        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);


        System.out.println("order: " + order);
        System.out.println("calc price: " + order.calculatePrice());
    }

}
