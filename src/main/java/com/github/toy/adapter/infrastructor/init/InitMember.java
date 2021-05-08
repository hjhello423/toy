package com.github.toy.adapter.infrastructor.init;

import com.github.toy.domain.item.Item;
import com.github.toy.domain.mamber.Address;
import com.github.toy.domain.mamber.Member;
import com.github.toy.domain.mamber.Team;
import com.github.toy.domain.order.Delivery;
import com.github.toy.domain.order.Order;
import com.github.toy.domain.order.OrderItem;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
        initMemberService.init2();
    }

    @Transactional
    @Component
    static class InitMemberService {

        @PersistenceContext
        private EntityManager em;

        public void init() {
            Team team = new Team("teamA");
            em.persist(team);

            Member member = createMember(team, 10, "memberA", "경기", "10번길", "1010");
            em.persist(member);

            Item item1 = createItem("JPA", 1000, 10);
            Item item2 = createItem("Spring", 2000, 15);
            em.persist(item1);
            em.persist(item2);

            OrderItem orderItem1 = OrderItem.createOrderItem(item1, 1000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(item2, 4000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        public void init2() {
            Team team = new Team("teamB");
            em.persist(team);

            Member member = createMember(team, 10, "memberB", "경기", "15번길", "1515");

            em.persist(member);

            Item item1 = createItem("DB", 3000, 20);
            Item item2 = createItem("Java", 4000, 15);
            em.persist(item1);
            em.persist(item2);

            OrderItem orderItem1 = OrderItem.createOrderItem(item1, 1000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(item2, 4000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress((member.getAddress()));
            return delivery;
        }

        private Item createItem(String name, int price, int quantity) {
            return new Item(name, price, quantity);
        }

        private Member createMember(Team team, int age, String userName, String city, String street, String zipcode) {
            Member member = new Member(userName, age, team);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }

}
