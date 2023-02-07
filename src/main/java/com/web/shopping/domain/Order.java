package com.web.shopping.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Account account;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'READY'")
    private OrderStatus orderStatus = OrderStatus.READY;

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Delivery delivery;

    @Column(nullable = false)
    private int orderPrice;

}