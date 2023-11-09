package com.example.bankback.business;

import com.example.bankback.data.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    private TransactionDTO transactionDTO;

    @BeforeEach
    public void setUp() {
        transactionDTO = new TransactionDTO();
        transactionDTO.setDateCreated(LocalDateTime.now());
        transactionDTO.setDateExecuted(LocalDateTime.now());
        transactionDTO.setDebtor("12345678901");
        transactionDTO.setCreditor("10987654321");
        transactionDTO.setAmount(new BigDecimal("500.00"));
        transactionDTO.setCurrency("EUR");
    }

    @Test
    public void testCreateTransaction() {
        TransactionDTO createdTransaction = transactionService.create(transactionDTO);
        assertNotNull(createdTransaction.getId());
    }

    @Test
    public void testReadTransaction() {
        TransactionDTO createdTransaction = transactionService.create(transactionDTO);
        assertTrue(transactionService.readById(createdTransaction.getId()).isPresent());
    }

    @Test
    public void testUpdateTransaction() {
        TransactionDTO createdTransaction = transactionService.create(transactionDTO);
        createdTransaction.setAmount(new BigDecimal("1000.00"));
        transactionService.update(createdTransaction, createdTransaction.getId());

        TransactionDTO updatedTransaction = transactionService.readById(createdTransaction.getId()).orElse(null);
        assertNotNull(updatedTransaction);
        assertEquals(new BigDecimal("1000.00"), updatedTransaction.getAmount());
    }

    @Test
    public void testDeleteTransaction() {
        TransactionDTO createdTransaction = transactionService.create(transactionDTO);
        transactionService.deleteById(createdTransaction.getId());
        assertFalse(transactionService.readById(createdTransaction.getId()).isPresent());
    }

    @Test
    public void testReadAllTransactions() {
        transactionService.create(transactionDTO);
        assertFalse(transactionService.readAll().isEmpty());
    }
}
