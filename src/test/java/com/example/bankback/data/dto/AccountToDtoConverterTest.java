package com.example.bankback.data.dto;

import com.example.bankback.data.dto.converters.AccountToDtoConverter;
import com.example.bankback.data.dto.converters.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.User;
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
class AccountToDtoConverterTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountToDtoConverter accountToDtoConverter;

    @Autowired
    private DtoToAccountConverter dtoToAccountConverter;

    @Test
    void testAccountToDtoAndDtoToAccountConversion() {
        User newUser = new User();
        newUser.setFirstName("Kirin");
        newUser.setLastName("Dorian");
        newUser.setBirthDate(LocalDate.of(1990, 5, 12));


        User savedUser = userRepository.save(newUser);


        Account newAccount = new Account();
        newAccount.setName("Savings Account");
        newAccount.setIBAN("CZ123456789");
        newAccount.setBalance(new BigDecimal("1000.00"));
        newAccount.setCurrency("CZK");
        newAccount.setUser(savedUser);


        AccountDTO accountDTO = accountToDtoConverter.apply(newAccount);
        assertThat(accountDTO.getUserId()).isEqualTo(savedUser.getId());
        assertThat(accountDTO.getIBAN()).isEqualTo(newAccount.getIBAN());
        assertThat(accountDTO.getName()).isEqualTo(newAccount.getName());
        assertThat(accountDTO.getBalance()).isEqualByComparingTo(newAccount.getBalance());
        assertThat(accountDTO.getCurrency()).isEqualTo(newAccount.getCurrency());


        Account convertedAccount = dtoToAccountConverter.apply(accountDTO);
        assertThat(convertedAccount.getUser()).isEqualTo(savedUser);
        assertThat(convertedAccount.getIBAN()).isEqualTo(accountDTO.getIBAN());
        assertThat(convertedAccount.getName()).isEqualTo(accountDTO.getName());
        assertThat(convertedAccount.getBalance()).isEqualByComparingTo(accountDTO.getBalance());
        assertThat(convertedAccount.getCurrency()).isEqualTo(accountDTO.getCurrency());
    }
}

