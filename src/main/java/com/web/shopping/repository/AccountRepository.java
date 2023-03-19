package com.web.shopping.repository;

import com.web.shopping.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

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

    public Optional<Account> findByEmail(String email){
        List<Account> account = em.createQuery("select a from Account a where a.email= :email", Account.class)
                .setParameter("email", email)
                .getResultList();
        return account.stream().findAny();
    }

    public Account findById(long id){
        return em.createQuery("select a from Account a where a.id = :id", Account.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
