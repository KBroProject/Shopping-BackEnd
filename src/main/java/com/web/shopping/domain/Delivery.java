package com.web.shopping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DELIVERY_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_ID")
    private Order orders;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'READY'")
    private DeliveryStatus deliveryStatus = DeliveryStatus.READY;
}
