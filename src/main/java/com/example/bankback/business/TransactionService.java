package com.example.bankback.business;

import com.example.bankback.data.dto.TransactionDTO;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.Transaction;
import com.example.bankback.data.repository.AccountRepository;
import com.example.bankback.data.repository.TransactionRepository;
import com.example.bankback.data.dto.converters.transaction.DtoToTransactionConverter;
import com.example.bankback.data.dto.converters.transaction.TransactionToDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService extends AbstractCrudService<TransactionDTO, Long, Transaction, TransactionRepository> {

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository repository,
                              DtoToTransactionConverter toEntityConverter,
                              TransactionToDtoConverter toDtoConverter,
                              ModelMapper modelMapper,
                              AccountRepository accountRepository
                              ) {
        super(repository, toEntityConverter, toDtoConverter);
        this.modelMapper = modelMapper;
        this.accountRepository=accountRepository;
    }

    @Override
    @Transactional
    public void update(TransactionDTO dto, Long id) {
        Transaction existingTransaction = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction not found with id " + id));
        Transaction updatedTransaction = toEntityConverter.apply(dto);
        updatedTransaction.setId(existingTransaction.getId());
        modelMapper.map(updatedTransaction, existingTransaction);
        repository.save(existingTransaction);
    }

    public List<TransactionDTO> findAllByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id " + accountId));

        String accountIban = account.getIBAN();
        List<Transaction> transactions = repository.findByCreditorOrDebtor(accountIban, accountIban);
        return transactions.stream()
                .map(toDtoConverter)
                .collect(Collectors.toList());
    }

}

