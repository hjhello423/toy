package com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.query;

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
}
