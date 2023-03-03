package com.web.shopping.service;

import com.web.shopping.dto.RequestAccountDto;
import com.web.shopping.entity.Account;
import com.web.shopping.exception.CustomException;
import com.web.shopping.exception.ErrorCode;
import com.web.shopping.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true) // GET 많을때 기재, POST 많을땐 여기다가 @Transaction
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    // 중복 검사 체크
    public void validateDuplicateAccount(String email) {
        Optional<Account> findAccount = accountRepository.findByEmail(email);
        if(findAccount.isPresent()){
            throw new CustomException(ErrorCode.SAME_EMAIL);
        }
    }

    // 유저 정보가져오기(selectOne) + 로그인
    public Account selectAccount(String email, String password) {
        Optional<Account> findAccount = accountRepository.findByEmail(email);
        if(findAccount.isPresent()) {
            if(bCryptPasswordEncoder.matches(password, findAccount.get().getPassword())) {
                return findAccount.get();
            } else {
                throw new CustomException(ErrorCode.FAIL_PASSWORD);
            }
        } else {
            throw new CustomException(ErrorCode.NO_USER);
        }
    }


    // 회원 가입
    @Transactional
    public Long save(RequestAccountDto requestAccountDto){
        validateDuplicateAccount(requestAccountDto.getEmail());
        Account account = requestAccountDto.toEntiy();
        account.hashPassword(bCryptPasswordEncoder);
        return accountRepository.save(account);
    }
}
