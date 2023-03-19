package com.web.shopping.service;

import com.web.shopping.dto.CreateItemResponseDto;
import com.web.shopping.dto.RequestItemDto;
import com.web.shopping.entity.Account;
import com.web.shopping.entity.Item;
import com.web.shopping.repository.AccountRepository;
import com.web.shopping.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final AccountRepository accountRepository;

    public CreateItemResponseDto save(RequestItemDto requestItemDto){
        Account account = accountRepository.findById(requestItemDto.getSeller());

        Item item = Item.builder()
                        .name(requestItemDto.getItemName())
                        .price(requestItemDto.getItemPrice())
                        .stockQuantity(requestItemDto.getStockQuantity())
                        .seller(account)
                        .build();

        itemRepository.save(item);
        return CreateItemResponseDto.builder()
                .id(item.getId())
                .seller(item.getSeller().getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getStockQuantity())
                .build();
    }
}
