package com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.query;

import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderItemQueryDto;
import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderQueryDto;
import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDots() {
        return em.createQuery("select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderSimpleQueryDto(o.id, m.username, o.orderDate, o.status, d.address)" +
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

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.dto.OrderQueryDto(o.id, m.username, o.orderDate, o.status, d.address) from Order o" +
                " join o.member m" +
                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
