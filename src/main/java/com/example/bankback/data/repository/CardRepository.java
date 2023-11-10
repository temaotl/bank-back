package com.example.bankback.data.repository;

import com.example.bankback.data.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends CrudRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);
}