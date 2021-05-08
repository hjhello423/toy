package com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto;

import com.github.toy.domain.mamber.Address;
import com.github.toy.enums.OrderStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderFlatDto {

    private Long orderId;
    private String userName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderFlatDto(Long orderId, String userName, LocalDateTime orderDate, OrderStatus orderStatus,
        Address address, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

}
