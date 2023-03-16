package com.web.shopping.service;

import com.web.shopping.dto.RequestAccountDto;
import com.web.shopping.entity.Account;
import com.web.shopping.entity.RoleEnum;
import com.web.shopping.exception.CustomException;
import com.web.shopping.exception.ErrorCode;
import com.web.shopping.repository.AccountRepository;
import com.web.shopping.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true) // GET 많을때 기재, POST 많을땐 여기다가 @Transaction
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    // 중복 검사 체크
    public void validateDuplicateAccount(String email) {
        if(accountRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.SAME_EMAIL);
        }
    }

    // 유저 정보가져오기(selectOne) + 로그인
    public Map<String, String> selectAccount(String email, String password) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_USER));
        if(!bCryptPasswordEncoder.matches(password, account.getPassword())) {
            throw new CustomException(ErrorCode.FAIL_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createAccessToken(account.getEmail()
                , account.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(account.getEmail()
                , account.getRole());
        redisTemplate.opsForValue().set("refreshToken:"+account.getEmail(), refreshToken, 24*14*3600L, TimeUnit.MILLISECONDS);


        Map<String, String> tokenSet = new HashMap<>();
        tokenSet.put("accessToken", accessToken);
        tokenSet.put("refreshToken", refreshToken);

//        refreshTokenRepository.save(
//                RefreshToken.builder()
//                    .token(refreshToken)
//                    .email(account.getEmail())
//                    .role(account.getRole().toString())
//                    .build()
//        );

        // DB에 토큰 넣는부분 추가
        return tokenSet;
    }


    // 회원 가입
    @Transactional
    public Long save(RequestAccountDto requestAccountDto){
        validateDuplicateAccount(requestAccountDto.getEmail());
        Account account = requestAccountDto.toEntiy();
        account.hashPassword(bCryptPasswordEncoder);
        return accountRepository.save(account);
    }
    
    // accessToken 재발급
    public String reissueAccessToken(String token){
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(token)) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        String userRefreshToken = redisTemplate.opsForValue().get("refreshToken:"+authentication.getName());

        if(ObjectUtils.isEmpty(userRefreshToken)) {
            throw new CustomException(ErrorCode.NO_REDIS_TOKEN);
        }
        if(!userRefreshToken.equals(token)) {
            throw new CustomException(ErrorCode.NO_EQUAL_TOKEN);
        }

        String accessToken = jwtTokenProvider.createAccessToken(authentication.getName(),
               RoleEnum.valueOf(authentication.getAuthorities()
                       .stream()
                       .map(GrantedAuthority::getAuthority)
                       .collect(Collectors.toList())
                       .get(0)));

        return accessToken;
    }

    // 로그아웃
    public void logout(String auth) {
        String accessToken = auth.substring(7);
        if(!jwtTokenProvider.validateToken(accessToken)) {
            throw new CustomException(ErrorCode.LOGOUT_INVALID_TOKEN);
        }

        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        String userRefreshToken = redisTemplate.opsForValue().get("refreshToken:" + authentication.getName());
        // Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제
        if(userRefreshToken != null) {
            // Refresh Token 삭제
            redisTemplate.delete("refreshToken:" + authentication.getName());
        }

        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        // 기존 accessToken 블랙리스트(Redis)에 추가
        redisTemplate.opsForValue().set("blackList:"+accessToken, "logout", expiration, TimeUnit.MILLISECONDS);

    }
}
