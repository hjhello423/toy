package com.github.toy.adapter.presentation.web.api;

import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.OrderJpqlRepository;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.OrderSearch;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderSimpleQueryDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.query.OrderQueryRepository;
import com.github.toy.member.domain.Address;
import com.github.toy.domain.order.Order;
import com.github.toy.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderJpqlRepository orderJpqlRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderJpqlRepository.findAll(new OrderSearch());

        for (Order order : all) {
            order.getMember().getUsername(); // lazy 강제 초기화
            order.getDelivery().getAddress();
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {
        List<Order> orders = orderJpqlRepository.findAll(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderJpqlRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
            .map(SimpleOrderDto::new)
            .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderQueryRepository.findOrderDots();
    }

    @Data
    static class SimpleOrderDto {

        private Long orderId;
        private String userName;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.userName = order.getMember().getUsername();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }

}
