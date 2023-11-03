package com.example.bankback.data.repository;


import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CardRepositoryTests {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAndFindCard() {

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Testovic");
        user.setBirthDate(LocalDate.of(1980, 1, 1));
        User savedUser = userRepository.save(user);

        Account newAccount = new Account();
        newAccount.setName("Savings Account");
        newAccount.setIBAN("CZ1234256789");
        newAccount.setBalance(new BigDecimal("1500.00"));
        newAccount.setCurrency("CZK");
        newAccount.setUser(savedUser);


        Account savedAccount = accountRepository.save(newAccount);


        Card newCard = new Card();
        newCard.setName("Test Card");
        newCard.setBlocked(false);
        newCard.setAccount(savedAccount);


        Card savedCard = cardRepository.save(newCard);


        assertThat(savedCard.getId()).isNotNull();


        assertThat(savedCard.getName()).isEqualTo(newCard.getName());
        assertThat(savedCard.isBlocked()).isEqualTo(newCard.isBlocked());
        assertThat(savedCard.getAccount()).isEqualTo(savedAccount);

        Optional<Card> foundCard = cardRepository.findById(savedCard.getId());
        assertThat(foundCard.isPresent()).isTrue();
        assertThat(foundCard.get()).isEqualTo(savedCard);
    }
}

