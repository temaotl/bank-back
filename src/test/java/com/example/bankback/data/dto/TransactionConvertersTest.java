package com.example.bankback.data.dto;

import com.example.bankback.data.dto.converters.transaction.DtoToTransactionConverter;
import com.example.bankback.data.dto.converters.transaction.TransactionToDtoConverter;
import com.example.bankback.data.entity.Transaction;
import com.example.bankback.data.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class TransactionConvertersTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionToDtoConverter transactionToDtoConverter;

    @Autowired
    private DtoToTransactionConverter dtoToTransactionConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void testTransactionToDtoAndDtoToTransactionConversion() {
        // Prepare a transaction entity
        Transaction transaction = new Transaction();
        transaction.setDateCreated(LocalDateTime.now());
        transaction.setDateExecuted(LocalDateTime.now());
        transaction.setDebtor("12345678901");
        transaction.setCreditor("10987654321");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency("USD");

        // Save the transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Convert to DTO
        TransactionDTO transactionDTO = transactionToDtoConverter.apply(savedTransaction);

        // Assertions for conversion to DTO
        assertThat(transactionDTO.getId()).isEqualTo(savedTransaction.getId());
        assertThat(transactionDTO.getDebtor()).isEqualTo(savedTransaction.getDebtor());
        assertThat(transactionDTO.getCreditor()).isEqualTo(savedTransaction.getCreditor());
        assertThat(transactionDTO.getAmount()).isEqualTo(savedTransaction.getAmount());
        assertThat(transactionDTO.getCurrency()).isEqualTo(savedTransaction.getCurrency());

        // Convert back to entity
        Transaction convertedTransaction = dtoToTransactionConverter.apply(transactionDTO);

        // Assertions for conversion from DTO
        assertThat(convertedTransaction.getId()).isEqualTo(transactionDTO.getId());
        assertThat(convertedTransaction.getDebtor()).isEqualTo(transactionDTO.getDebtor());
        assertThat(convertedTransaction.getCreditor()).isEqualTo(transactionDTO.getCreditor());
        assertThat(convertedTransaction.getAmount()).isEqualTo(transactionDTO.getAmount());
        assertThat(convertedTransaction.getCurrency()).isEqualTo(transactionDTO.getCurrency());
    }
}


