package com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto;

import com.github.hjdeepsleep.toy.domain.mamber.Address;
import com.github.hjdeepsleep.toy.enums.OrderStatus;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderSimpleQueryDto {

    private Long orderId;
    private String userName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long orderId, String userName, LocalDateTime orderDate, OrderStatus orderStatus,
        Address address) {
        this.orderId = orderId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

}
