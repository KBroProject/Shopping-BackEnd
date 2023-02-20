package com.web.shopping.controller;

import com.web.shopping.entity.Account;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class AccountController {

    @GetMapping("/test")
    public TestDto helloWord(){
        TestDto testDto = new TestDto("Hello~");
        List<Account> account = new ArrayList<>();
        List<TestDto> accountDto = account.stream()
                .map(a -> new TestDto(a.getName()))
                .collect(Collectors.toList());

        return testDto;
    }

    @Data
    class TestDto{
        private String test;
        public TestDto(String test){
            this.test = test;
        }
    }


}
