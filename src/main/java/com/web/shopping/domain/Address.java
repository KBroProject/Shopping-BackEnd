package com.web.shopping.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

    @Column(name = "ADDRESS", length = 50)
    private String address;

    @Column(name = "DETAIL_ADDRESS", length = 50)
    private String detailAddress;

    @Column(name = "ZIPCODE", length = 10)
    private String zipcode;

}
