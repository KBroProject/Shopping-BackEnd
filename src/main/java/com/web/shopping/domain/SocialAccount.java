package com.web.shopping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SocialAccount {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SOCAIL_ACCOUNT_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(length = 100)
    private String UUID;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private SocialEnum provider;
}
