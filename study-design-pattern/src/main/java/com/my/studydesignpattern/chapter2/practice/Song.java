package com.my.studydesignpattern.chapter2.practice;

import lombok.Getter;

public class Song {

    @Getter
    private DiscountType discountType;
    private double price = 10;

    public Song(DiscountType type) {
        this.discountType = type;
    }

    public double getPaymentAmount() {
        return discountType.calcPrice(this.price);
    }

}
