package com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemQueryDto {

    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

}
