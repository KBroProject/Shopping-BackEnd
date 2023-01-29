package com.web.shopping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Entity
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'INACTIVE'")
    private StatusEnum status = StatusEnum.INACTIVE;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'BUYER'")
    private RoleEnum role;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();
    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    // 로그인하면 Spring security 로그인 성공 시 lastLogin을 update
    private LocalDateTime lastLogin;
}
