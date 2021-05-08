package com.github.toy.adapter.presentation.web.api;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.OrderJpqlRepository;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.OrderSearch;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderFlatDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderItemQueryDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderQueryDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.query.OrderQueryRepository;
import com.github.toy.member.domain.Address;
import com.github.toy.domain.order.Order;
import com.github.toy.domain.order.OrderItem;
import com.github.toy.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderJpqlRepository orderJpqlRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderJpqlRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getUsername();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(o -> o.getItem().getName());
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderJpqlRepository.findAll(new OrderSearch());
        List<OrderDto> collect = orders.stream()
            .map(OrderDto::new)
            .collect(toList());

        return collect;
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> orders = orderJpqlRepository.findAllWithItem();
        List<OrderDto> collect = orders.stream()
            .map(OrderDto::new)
            .collect(toList());

        return collect;
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersPageV3(
        @RequestParam(value = "offset", defaultValue = "0") int offset,
        @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderJpqlRepository.findAllWithMemberDelivery(offset, limit);
        List<OrderDto> collect = orders.stream()
            .map(OrderDto::new)
            .collect(toList());

        return collect;
    }

    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDots();
    }

    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5() {
        return orderQueryRepository.findOrderQueryDots2();
    }

    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findOrderQueryDots3();

        return flats.stream()
            .collect(groupingBy(
                o -> new OrderQueryDto(o.getOrderId(), o.getUserName(), o.getOrderDate(), o.getOrderStatus(),
                    o.getAddress()),
                mapping(o -> new OrderItemQueryDto(o.getOrderId(), o.getItemName(), o.getOrderPrice(), o.getCount()),
                    toList())))
            .entrySet().stream()
            .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getUserName(), e.getKey().getOrderDate(),
                e.getKey().getOrderStatus(), e.getKey().getAddress(), e.getValue()))
            .collect(toList());
    }

    @Getter
    static class OrderDto {

        private Long orderId;
        private String username;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.username = order.getMember().getUsername();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems()
                .stream()
                .map(OrderItemDto::new)
                .collect(toList());
        }
    }

    @Getter
    static class OrderItemDto {

        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }

}
