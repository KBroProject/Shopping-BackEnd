package com.web.shopping.controller;

import com.web.shopping.dto.RequestAccountDto;
import com.web.shopping.entity.Account;
import com.web.shopping.service.AccountService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;

//    @GetMapping("/test")
//    public TestDto helloWord(){
//        TestDto testDto = new TestDto("Hello~");
//        List<Account> account = new ArrayList<>();
//        List<TestDto> accountDto = account.stream()
//                .map(a -> new TestDto(a.getName()))
//                .collect(Collectors.toList());
//        return testDto;
//    }

    @PostMapping("/members")
    public long joinMembers(@RequestBody @Valid RequestAccountDto requestAccountDto) {

        long id = accountService.save(requestAccountDto);

        return id;
    }

    @GetMapping("/login")
    public String loginMembers(@RequestBody RequestAccountDto requestAccountDto) {
        String resultMsg = accountService.selectAccount(requestAccountDto.getEmail(), requestAccountDto.getPassword());

        return resultMsg;
    }

//    @Data
//    class TestDto{
//        private String test;
//        public TestDto(String test){
//            this.test = test;
//        }
//    }
}
