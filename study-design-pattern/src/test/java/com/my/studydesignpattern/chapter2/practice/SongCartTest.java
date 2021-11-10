package com.my.studydesignpattern.chapter2.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SongCartTest {


    @DisplayName("금액 할인 테스트")
    @Test
    void SongDiscount() {
        // given
        Song nonDiscount = new Song(DiscountType.NON_DISCOUNT);

        // when

        System.out.println(nonDiscount);
        // then
    }

    @DisplayName("연습문제 2-4")
    @Test
    void cartTest() {
    }

}