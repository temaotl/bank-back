package com.example.bankback.data.repository;

import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AccountRepositoryTests {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAccount() {

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Testovic");
        user.setBirthDate(LocalDate.of(1980, 1, 1));
        User savedUser = userRepository.save(user);


        Account newAccount = new Account();
        newAccount.setName("Savings Account");
        newAccount.setIBAN("CZ123456789");
        newAccount.setBalance(new BigDecimal("1000.00"));
        newAccount.setCurrency("CZK");
        newAccount.setUser(savedUser);


        Account savedAccount = accountRepository.save(newAccount);

        assertThat(savedAccount.getId()).isNotNull();
        assertThat(savedAccount.getName()).isEqualTo(newAccount.getName());
        assertThat(savedAccount.getIBAN()).isEqualTo(newAccount.getIBAN());
        assertThat(savedAccount.getBalance()).isEqualTo(newAccount.getBalance());
        assertThat(savedAccount.getCurrency()).isEqualTo(newAccount.getCurrency());
        assertThat(savedAccount.getUser()).isEqualTo(savedUser);
    }
}
