package com.example.bankback.data.dto.converters;

import com.example.bankback.data.dto.TransactionDTO;
import com.example.bankback.data.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DtoToTransactionConverter implements Function<TransactionDTO, Transaction> {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoToTransactionConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Transaction apply(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, Transaction.class);
    }
}