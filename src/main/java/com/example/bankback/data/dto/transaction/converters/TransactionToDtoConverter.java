package com.example.bankback.data.dto.transaction.converters;

import com.example.bankback.data.dto.transaction.TransactionDTO;
import com.example.bankback.data.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TransactionToDtoConverter implements Function<Transaction, TransactionDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public TransactionToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDTO apply(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDTO.class);
    }
}

