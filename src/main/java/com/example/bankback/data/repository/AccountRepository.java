package com.example.bankback.data.repository;

import com.example.bankback.data.entity.Account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
    Optional<Account> findByIBAN(String iban);

    boolean existsByIBAN(String iban);
}