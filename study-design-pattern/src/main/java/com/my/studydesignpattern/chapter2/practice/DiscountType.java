package com.my.studydesignpattern.chapter2.practice;

import java.util.function.Function;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DiscountType {

    NON_DISCOUNT(operand -> operand),
    ON_SALE(operand -> operand * 0.9),
    TODAY_EVENT(operand -> operand * 0.7);

    private Function<Double, Double> calc;

    public double calcPrice(double operand) {
        return calc.apply(operand);
    }

}
