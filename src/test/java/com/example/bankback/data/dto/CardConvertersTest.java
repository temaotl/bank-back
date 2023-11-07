package com.example.bankback.data.dto;


import com.example.bankback.data.dto.converters.CardToDtoConverter;
import com.example.bankback.data.dto.converters.DtoToCardConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.entity.User;
import com.example.bankback.data.repository.AccountRepository;
import com.example.bankback.data.repository.CardRepository;
import com.example.bankback.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CardConvertersTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardToDtoConverter cardToDtoConverter;

    @Autowired
    private DtoToCardConverter dtoToCardConverter;

    @Test
    void testCardToDtoAndDtoToCardConversion() {

        User newUser = new User();
        newUser.setFirstName("Kirin");
        newUser.setLastName("Dorian");
        newUser.setBirthDate(LocalDate.of(1990, 5, 12));
        newUser.setPassport("123456789");
        newUser.setPhoneNumber("+1234567890");
        newUser.setEmail("kirin.dorian@example.com");
        newUser.setAddress("123 Main St");

        User savedUser = userRepository.save(newUser);


        Account newAccount = new Account();
        newAccount.setName("Savings Account");
        newAccount.setAccountNumber("123455");
        newAccount.setIBAN("CZ123456789");
        newAccount.setBalance(new BigDecimal("1000.00"));
        newAccount.setCurrency("CZK");
        newAccount.setUser(savedUser);


        Account savedAccount = accountRepository.save(newAccount);


        Card card = new Card();
        card.setName("Personal Card");
        card.setBlocked(false);
        card.setAccount(savedAccount);
        Card savedCard = cardRepository.save(card);


        CardDTO cardDTO = cardToDtoConverter.apply(savedCard);


        assertThat(cardDTO.getId()).isEqualTo(savedCard.getId());
        assertThat(cardDTO.getName()).isEqualTo(savedCard.getName());
        assertThat(cardDTO.getBlocked()).isEqualTo(savedCard.isBlocked());
        assertThat(cardDTO.getDateLocked()).isEqualTo(savedCard.getDateLocked());
        assertThat(cardDTO.getAccountId()).isEqualTo(savedAccount.getId());


        Card convertedCard = dtoToCardConverter.apply(cardDTO);


        assertThat(convertedCard.getId()).isEqualTo(cardDTO.getId());
        assertThat(convertedCard.getName()).isEqualTo(cardDTO.getName());
        assertThat(convertedCard.isBlocked()).isEqualTo(cardDTO.getBlocked());
        assertThat(convertedCard.getDateLocked()).isEqualTo(cardDTO.getDateLocked());
        assertThat(convertedCard.getAccount().getId()).isEqualTo(cardDTO.getAccountId());
    }
}

