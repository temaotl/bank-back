package com.example.bankback.data.dto;

import com.example.bankback.data.dto.TransactionDTO;
import com.example.bankback.data.dto.converters.DtoToTransactionConverter;
import com.example.bankback.data.dto.converters.TransactionToDtoConverter;
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
        transaction.setDate(LocalDateTime.now());
        transaction.setSenderAccountNumber("123456789");
        transaction.setReceiverAccountNumber("987654321");
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency("USD");


        Transaction savedTransaction = transactionRepository.save(transaction);


        TransactionDTO transactionDTO = transactionToDtoConverter.apply(savedTransaction);


        assertThat(transactionDTO.getId()).isEqualTo(savedTransaction.getId());
        assertThat(transactionDTO.getSenderAccountNumber()).isEqualTo(savedTransaction.getSenderAccountNumber());
        assertThat(transactionDTO.getReceiverAccountNumber()).isEqualTo(savedTransaction.getReceiverAccountNumber());
        assertThat(transactionDTO.getAmount()).isEqualTo(savedTransaction.getAmount());
        assertThat(transactionDTO.getCurrency()).isEqualTo(savedTransaction.getCurrency());


        Transaction convertedTransaction = dtoToTransactionConverter.apply(transactionDTO);


        assertThat(convertedTransaction.getId()).isEqualTo(transactionDTO.getId());
        assertThat(convertedTransaction.getSenderAccountNumber()).isEqualTo(transactionDTO.getSenderAccountNumber());
        assertThat(convertedTransaction.getReceiverAccountNumber()).isEqualTo(transactionDTO.getReceiverAccountNumber());
        assertThat(convertedTransaction.getAmount()).isEqualTo(transactionDTO.getAmount());
        assertThat(convertedTransaction.getCurrency()).isEqualTo(transactionDTO.getCurrency());
    }
}

