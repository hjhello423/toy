package com.github.toy.adapter.infrastructor.repository.jqpl_test.order.query;

import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderFlatDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderItemQueryDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderQueryDto;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderSimpleQueryDto;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDots() {
        return em.createQuery(
            "select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderSimpleQueryDto(o.id, m.username, o.orderDate, o.status, d.address)"
                +
                "from Order o " +
                "join o.member m " +
                "join o.delivery d ", OrderSimpleQueryDto.class)
            .getResultList();
    }

    public List<OrderQueryDto> findOrderQueryDots() {
        List<OrderQueryDto> result = findOrders();

        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    public List<OrderQueryDto> findOrderQueryDots2() {
        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderitemMap(toOrderIds(result));

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    public List<OrderFlatDto> findOrderQueryDots3() {
        return em.createQuery(
            "select new " +
                " com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderFlatDto(o.id, m.username, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)"
                +
                " from Order o" +
                " join o.member m" +
                " join o.delivery d" +
                " join o.orderItems oi" +
                " join oi.item i ", OrderFlatDto.class)
            .getResultList();
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderitemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
            "select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
                +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
            .setParameter("orderIds", orderIds)
            .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
            .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
        return orderItemMap;
    }

    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream()
            .map(OrderQueryDto::getOrderId)
            .collect(Collectors.toList());
        return orderIds;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
            "select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)"
                +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id = :orderId", OrderItemQueryDto.class)
            .setParameter("orderId", orderId)
            .getResultList();
    }


    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
            "select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address) from Order o"
                +
                " join o.member m" +
                " join o.delivery d", OrderQueryDto.class)
            .getResultList();
    }

}
