package com.github.hjdeepsleep.toy.adapter.presentation.web.api;

import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.OrderJpqlRepository;
import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.OrderSearch;
import com.github.hjdeepsleep.toy.domain.order.Order;
import com.github.hjdeepsleep.toy.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderJpqlRepository orderJpqlRepository;

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
}
