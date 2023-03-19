package com.web.shopping.controller;


import com.web.shopping.dto.CreateItemResponseDto;
import com.web.shopping.dto.RequestItemDto;
import com.web.shopping.dto.ResponseDto;
import com.web.shopping.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items/create")
    public ResponseDto createItem(@RequestBody @Valid RequestItemDto request){
        CreateItemResponseDto createItemResponseDto = itemService.save(request);
        return new ResponseDto(200, "성공", "상품 생성 성공",createItemResponseDto);
    }
}
