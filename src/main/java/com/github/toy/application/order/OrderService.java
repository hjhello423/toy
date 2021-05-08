package com.github.toy.application.order;

import com.github.toy.adapter.infrastructor.repository.jqpl_test.item.ItemJpqlRepository;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.member.MemberJpqlRepository;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.OrderJpqlRepository;
import com.github.toy.adapter.infrastructor.repository.jqpl_test.order.OrderSearch;
import com.github.toy.domain.item.Item;
import com.github.toy.domain.mamber.Member;
import com.github.toy.domain.order.Delivery;
import com.github.toy.domain.order.Order;
import com.github.toy.domain.order.OrderItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderJpqlRepository orderJpqlRepository;
    private final MemberJpqlRepository memberJpqlRepository;
    private final ItemJpqlRepository itemJpqlRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberJpqlRepository.findOne(memberId);
        Item item = itemJpqlRepository.findOne(itemId);

        //배송정보 조회
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderJpqlRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 검색
        Order order = orderJpqlRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderJpqlRepository.findAll(orderSearch);
    }

}
