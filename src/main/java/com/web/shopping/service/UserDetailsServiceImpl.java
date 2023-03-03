package com.web.shopping.service;

import com.web.shopping.entity.Account;
import com.web.shopping.exception.CustomException;
import com.web.shopping.exception.ErrorCode;
import com.web.shopping.repository.AccountRepository;
import com.web.shopping.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByEmail(email);
        if(account.isPresent()) {
            return new UserDetailsImpl(account.get());
        } else {
            throw new CustomException(ErrorCode.NO_USER);
        }
    }
}
