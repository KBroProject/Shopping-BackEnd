package com.web.shopping.controller;

import com.web.shopping.dto.RequestAccountDto;
import com.web.shopping.dto.ResponseDto;
import com.web.shopping.entity.Account;
import com.web.shopping.exception.CustomException;
import com.web.shopping.exception.ErrorCode;
import com.web.shopping.security.JwtTokenProvider;
import com.web.shopping.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

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

    @PostMapping("/login")
    public ResponseDto loginMembers(@RequestBody RequestAccountDto requestAccountDto, HttpServletResponse response, HttpServletRequest request) {
        if(request.getHeader("Authorization") != null || request.getHeader("Refresh") != null) {
            throw new CustomException(ErrorCode.LOGIN_TOKEN_DETECTED);
        }
        Map<String, String> tokenSet = accountService.selectAccount(requestAccountDto.getEmail(), requestAccountDto.getPassword());
        response.setHeader("Authorization", "Bearer " + tokenSet.get("accessToken"));

        return new ResponseDto(200, "success", "로그인 성공", tokenSet);

    }

    @PostMapping("/refresh")
    public ResponseDto genAccessToken(HttpServletRequest request){
        String refreshToken = request.getHeader("Refresh");
        if(refreshToken == null || !refreshToken.startsWith("Bearer "))
            throw new CustomException(ErrorCode.INVALID_TOKEN);

        refreshToken = refreshToken.split(" ")[1];
        String accessToken = accountService.reissueAccessToken(refreshToken);
        return new ResponseDto(200, "success", "토큰 정보가 갱신되었습니다.", accessToken);
    }

    @GetMapping("/logout")
    public ResponseDto logoutMembers(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if(accessToken == null) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }
        accountService.logout(accessToken);

        return new ResponseDto(200, "success", "로그아웃 되었습니다.", "");
    }

//    @Data
//    class TestDto{
//        private String test;
//        public TestDto(String test){
//            this.test = test;
//        }
//    }
}
