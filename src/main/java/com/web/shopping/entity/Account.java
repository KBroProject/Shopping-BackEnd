package com.web.shopping.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String password;

    @Column(length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'INACTIVE'")
    private AccountStatus status = AccountStatus.INACTIVE;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'BUYER'")
    private RoleEnum role = RoleEnum.BUYER;

    @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    // 로그인하면 Spring security 로그인 성공 시 lastLogin을 update
    private LocalDateTime lastLogin;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SocialAccount socialAccount;

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @Builder
    public Account(String email, String name, String password, String phoneNumber) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Account hashPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public void createItem(Item item){
        this.items.add(item);
    }
}
