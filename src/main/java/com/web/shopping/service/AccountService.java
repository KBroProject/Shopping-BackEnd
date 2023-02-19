package com.web.shopping.service;

import com.web.shopping.domain.Account;
import com.web.shopping.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Long save(Account account){
        return accountRepository.save(account);
    }
}
