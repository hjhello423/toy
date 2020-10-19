package com.github.hjdeepsleep.toy.application.order;

import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.order.OrderJpqlRepository;
import com.github.hjdeepsleep.toy.domain.item.Book;
import com.github.hjdeepsleep.toy.domain.mamber.Address;
import com.github.hjdeepsleep.toy.domain.mamber.Member;
import com.github.hjdeepsleep.toy.domain.order.Order;
import com.github.hjdeepsleep.toy.enums.OrderStatus;
import com.github.hjdeepsleep.toy.exception.NotEnoughStockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderJpqlRepository orderJpqlRepository;

    @Test
    public void order() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderJpqlRepository.findOne(orderId);

        assertTrue(OrderStatus.ORDER.equals(findOrder.getStatus()));
        assertTrue(1 == findOrder.getOrderItems().size());
        assertTrue(10000* orderCount == findOrder.getTotalPrice());
        assertTrue(8 == book.getStockQuantity());
    }

    @Test
    public void order_quantity_over() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 11;

        //then
        assertThrows(NotEnoughStockException.class,
                () -> orderService.order(member.getId(), item.getId(), orderCount));
    }
    
    @Test
    public void order_calcel() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("시골 JPA", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);
        
        //then
        Order findOrder = orderJpqlRepository.findOne(orderId);

        assertTrue(OrderStatus.CANCEL.equals(findOrder.getStatus()));
        assertTrue(10 == item.getStockQuantity());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setUsername("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }
}