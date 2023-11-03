package com.example.bankback.data.repository;


import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CardRepositoryTests {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAndFindCard() {

        Account newAccount = new Account();
        newAccount.setIBAN("IBAN123");
        newAccount.setName("Test Account");
        newAccount.setBalance(new BigDecimal("1000"));
        newAccount.setCurrency("USD");



        Optional<User> user = userRepository.findById(1L);

        // Сохраняем аккаунт
        Account savedAccount = accountRepository.save(newAccount);

        // Создаем новую карту
        Card newCard = new Card();
        newCard.setName("Test Card");
        newCard.setBlocked(false);
        newCard.setAccount(savedAccount);

        // Сохраняем карту
        Card savedCard = cardRepository.save(newCard);

        // Проверяем, что ID новой карты не равно null
        assertThat(savedCard.getId()).isNotNull();

        // Проверяем, что данные карты соответствуют данным, которые мы сохранили
        assertThat(savedCard.getName()).isEqualTo(newCard.getName());
        assertThat(savedCard.isBlocked()).isEqualTo(newCard.isBlocked());
        assertThat(savedCard.getAccount()).isEqualTo(savedAccount);

        // Проверяем, что можно найти карту по ID
        Optional<Card> foundCard = cardRepository.findById(savedCard.getId());
        assertThat(foundCard.isPresent()).isTrue();
        assertThat(foundCard.get()).isEqualTo(savedCard);
    }
}

