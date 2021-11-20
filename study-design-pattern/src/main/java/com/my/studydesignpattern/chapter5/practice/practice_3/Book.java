package com.my.studydesignpattern.chapter5.practice.practice_3;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;

public class Book {

    private String sign;
    @Getter
    private LocalDateTime publicationDate;
    @Getter
    private BigDecimal price;

}
