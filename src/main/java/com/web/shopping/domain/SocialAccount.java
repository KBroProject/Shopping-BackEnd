package com.web.shopping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SocialAccount {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 100)
    private String UUID;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private SocialEnum provider;
}
