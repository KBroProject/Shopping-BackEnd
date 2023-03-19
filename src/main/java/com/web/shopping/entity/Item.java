package com.web.shopping.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private Long id;

    @Column(length = 45)
    private String name;

    private int price;

    private int stockQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account seller;

    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItems = new ArrayList<>();

    @Builder
    public Item(String name, int price, int stockQuantity, Account seller){
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.seller = seller;
        addSeller(seller);
    }

    public void addSeller(Account seller){
        seller.createItem(this);
    }
}
