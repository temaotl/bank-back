package com.example.bankback.business;

import static org.junit.jupiter.api.Assertions.*;


import com.example.bankback.data.dto.AccountDTO;
import com.example.bankback.data.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@Transactional
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    private AccountDTO accountDTO;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {

        userDTO = new UserDTO();
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setBirthDate(LocalDate.of(1980, 1, 1));
        userDTO.setPassport("1234567890");
        userDTO.setPhoneNumber("123-456-7890");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setAddress("123 Example St, Example City, EX");
        UserDTO createdUser = userService.create(userDTO);


        assertNotNull(createdUser.getId(), "User should be successfully created with an ID");


        accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("1234567890");
        accountDTO.setIBAN("DE89 3704 0044 0532 0130 00");
        accountDTO.setName("John Doe Account");
        accountDTO.setBalance(new BigDecimal("1000.00"));
        accountDTO.setCurrency("EUR");
        accountDTO.setUserId(createdUser.getId());
    }

    @Test
    public void testCreateReadUpdateDelete() {
        // Create
        AccountDTO createdAccount = accountService.create(accountDTO);
        assertNotNull(createdAccount.getId(), "Account should be successfully created with an ID");

        // Read
        AccountDTO foundAccount = accountService.readById(createdAccount.getId())
                .orElseThrow(() -> new AssertionError("Account should be found by ID"));

        // Update
        foundAccount.setName("Jane Doe Account");
        accountService.update(foundAccount, foundAccount.getId());

        AccountDTO updatedAccount = accountService.readById(foundAccount.getId())
                .orElseThrow(() -> new AssertionError("Updated account should be found"));
        assertEquals("Jane Doe Account", updatedAccount.getName(), "The name of the updated account should be changed to 'Jane Doe Account'");

        // Delete
        accountService.deleteById(updatedAccount.getId());
        assertTrue(accountService.readById(updatedAccount.getId()).isEmpty(), "Account should be deleted");
    }
}
