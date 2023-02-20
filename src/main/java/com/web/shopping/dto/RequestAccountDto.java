package com.web.shopping.dto;

import com.web.shopping.entity.Account;
import com.web.shopping.entity.RoleEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/* 회원 서비스 요청 RequestDTO 클래스 */
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
@Getter
public class RequestAccountDto {
    private String email;
    private String name;
    private String password;
    private String phoneNumber;
    private RoleEnum role;

    /* 암호화된 password */
    public void encryptPassword(String BCryptpassword) {
        this.password = BCryptpassword;
    }

    /* DTO -> Entity */
    public Account toEntiy() {
        return Account.builder()
                .email(email)
                .name(name)
                .password(password)
                .phoneNumber(phoneNumber)
                .role(role.BUYER)
                .build();
    }
}
