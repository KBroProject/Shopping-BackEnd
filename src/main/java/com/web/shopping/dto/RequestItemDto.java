package com.web.shopping.dto;

import com.web.shopping.entity.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class RequestItemDto {

    @NotNull(message = "판매자 ID는 필수 입력값입니다.")
    long seller;

    @NotBlank(message = "상품명은 필수 입력값입니다.")
    String itemName;

    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    @Positive(message = "상품 가격은 양수만 입력가능합니다.")
    int itemPrice;

    @NotNull(message = "재고량은 필수 입력값입니다.")
    @Positive(message = "재고량은 양수만 입력가능합니다.")
    int stockQuantity;

}
