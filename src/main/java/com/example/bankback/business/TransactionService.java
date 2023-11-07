package com.example.bankback.business;

import com.example.bankback.data.dto.TransactionDTO;
import com.example.bankback.data.entity.Transaction;
import com.example.bankback.data.repository.TransactionRepository;
import com.example.bankback.data.dto.converters.DtoToTransactionConverter;
import com.example.bankback.data.dto.converters.TransactionToDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class TransactionService extends AbstractCrudService<TransactionDTO, Long, Transaction, TransactionRepository> {

    private final ModelMapper modelMapper;
    @Autowired
    public TransactionService(TransactionRepository repository,
                              DtoToTransactionConverter toEntityConverter,
                              TransactionToDtoConverter toDtoConverter,
                              ModelMapper modelMapper) {
        super(repository, toEntityConverter, toDtoConverter);
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void update(TransactionDTO dto, Long id) {
        Transaction existingTransaction = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id " + id));
        modelMapper.map(dto, existingTransaction);
        repository.save(existingTransaction);
    }
}

