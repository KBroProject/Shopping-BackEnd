package com.web.shopping.service;

import com.web.shopping.dto.RequestAccountDto;
import com.web.shopping.entity.Account;
import com.web.shopping.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true) // GET 많을때 기재, POST 많을땐 여기다가 @Transaction
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    // 중복 검사 체크
    public void validateDuplicateAccount(String email) {
        List<Account> findAccounts = accountRepository.findByEmail(email);
        if(!findAccounts.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 유저 정보가져오기(selectOne) + 로그인
    public String selectAccount(String email, String password) {
        List<Account> findAccounts = accountRepository.findByEmail(email);
        if(!findAccounts.isEmpty()) {
            if(bCryptPasswordEncoder.matches(password, findAccounts.get(0).getPassword())) {
                return findAccounts.get(0).getName()+ "로그인 성공";
            } else {
                throw new IllegalStateException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalStateException("회원 정보가 없습니다.");
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
