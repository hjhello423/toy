package com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto;

import com.github.toy.member.domain.Address;
import com.github.toy.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {

    private Long orderId;
    private String userName;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String userName, LocalDateTime orderDate, OrderStatus orderStatus,
        Address address) {
        this.orderId = orderId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDto(Long orderId, String userName, LocalDateTime orderDate, OrderStatus orderStatus,
        Address address, List<OrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.userName = userName;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }

}
