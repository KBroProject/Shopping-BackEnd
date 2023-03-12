package com.web.shopping.dto;

import com.web.shopping.entity.RoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenUserDto {
    private String email;
    private RoleEnum role;

    @Builder
    public TokenUserDto(String email, RoleEnum role){
        this.email = email;
        this.role = role;
    }
}
