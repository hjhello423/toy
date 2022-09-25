package com.my;

import com.my.config.AppConfig;
import com.my.member.Grade;
import com.my.member.Member;
import com.my.member.MemberService;
import com.my.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);

        System.out.println("new Member: " + member.getName());
        System.out.println("find Member: " + findMember.getName());
    }

}
