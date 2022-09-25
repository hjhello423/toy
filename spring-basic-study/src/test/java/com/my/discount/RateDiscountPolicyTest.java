package com.my.discount;

import com.my.member.Grade;
import com.my.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("정률 할인 정책")
class RateDiscountPolicyTest {

    private RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @DisplayName("VIP는 10%의 할인이 적용된다")
    @Test
    void discount() {
        // given
        Member member = new Member(1L, "memnerVip", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member, 10_000);

        // then
        assertThat(discount).isEqualTo(1_000);
    }

    @DisplayName("VIP가 아니면 할인이 적용되지 않는다")
    @Test
    void discount_fail() {
        // given
        Member member = new Member(1L, "memnerBasic", Grade.BASIC);

        // when
        int discount = discountPolicy.discount(member, 10_000);

        // then
        assertThat(discount).isEqualTo(0);
    }

}