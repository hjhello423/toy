package com.my.studydesignpattern.chapter5.practice.practice_3;

import java.math.BigDecimal;

public interface PriceStrategy {

    BigDecimal calc(BigDecimal price);

}
