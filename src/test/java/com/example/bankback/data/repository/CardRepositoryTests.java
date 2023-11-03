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

        User newUser = new User();
        newUser.setFirstName("Kirin");
        newUser.setLastName("Dorian");
        newUser.setBirthDate(LocalDate.of(1990, 5, 12));
        newUser.setPassport("123456789");
        newUser.setPhoneNumber("+1234567890");
        newUser.setEmail("kirin.dorian@example.com");
        newUser.setAddress("123 Main St");

        Account newAccount = new Account();
        newAccount.setName("Savings Account");
        newAccount.setAccountNumber("123455");
        newAccount.setIBAN("CZ1234256789");
        newAccount.setBalance(new BigDecimal("1500.00"));
        newAccount.setCurrency("CZK");
        newAccount.setUser(newUser);


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

