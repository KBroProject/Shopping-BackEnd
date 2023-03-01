package com.web.shopping.repository;

import com.web.shopping.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccountRepository {
    private final EntityManager em;

    public Long save(Account account){
        if (findByEmail(account.getEmail()).isEmpty()){
            em.persist(account);
            return account.getId();
        }
        return -1L;
    }

    public List<Account> findByEmail(String email){
        return em.createQuery("select a from Account a where a.email= :email", Account.class)
                .setParameter("email", email)
                .getResultList();
    }
}
