package com.github.toy.adapter.infrastructor.repository.jqpl_test.order;

import com.github.toy.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

}
