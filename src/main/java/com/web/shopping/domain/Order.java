package com.web.shopping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Account account;

    @Column(length = 10)
    @ColumnDefault("'PREPARING'")
    private String orderStatus;

    @Column(length = 50, nullable = false)
    private String delivery;

    @Column(nullable = false)
    private int orderPrice;

}
