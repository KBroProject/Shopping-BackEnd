package com.web.shopping.controller;

import com.web.shopping.dto.RequestAccountDto;
import com.web.shopping.dto.ResponseDto;
import com.web.shopping.entity.Account;
import com.web.shopping.security.JwtTokenProvider;
import com.web.shopping.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccountController {

    private final AccountService accountService;
    private final JwtTokenProvider jwtTokenProvider;

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
    public ResponseDto loginMembers(@RequestBody RequestAccountDto requestAccountDto, HttpServletResponse response) {
        Map<String, String> tokenSet = accountService.selectAccount(requestAccountDto.getEmail(), requestAccountDto.getPassword());
        response.setHeader("Authorization", "Bearer " + tokenSet.get("accessToken"));

        return new ResponseDto(200, "token",tokenSet);

    }

//    @Data
//    class TestDto{
//        private String test;
//        public TestDto(String test){
//            this.test = test;
//        }
//    }
}
