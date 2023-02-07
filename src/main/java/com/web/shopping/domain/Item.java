package com.web.shopping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(name = "ITEM_NAME",length = 45)
    private String name;

    @Column(name = "ITEM_PRICE")
    private int price;

    @Column(name = "ITEM_STOCK_QUANTITY")
    private int stockQuantity;

    @Column(name = "SELLER")
    private String seller;
}
