package com.web.shopping.dto;

import com.web.shopping.entity.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CreateItemResponseDto {
    private long id;
    private long seller;
    private String name;
    private int price;
    private int quantity;

    @Builder
    public CreateItemResponseDto(long id, long seller, String name, int price, int quantity){
        this.id = id;
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
