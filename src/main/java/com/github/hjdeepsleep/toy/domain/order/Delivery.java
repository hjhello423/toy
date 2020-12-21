package com.github.hjdeepsleep.toy.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.hjdeepsleep.toy.domain.mamber.Address;
import com.github.hjdeepsleep.toy.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter @Setter
@Entity
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @Embedded
    private Address address;
    @Enumerated(STRING)
    private DeliveryStatus status;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;
}
