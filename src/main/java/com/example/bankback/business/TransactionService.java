package com.example.bankback.business;

import com.example.bankback.custom_exception.InsufficientFundsException;
import com.example.bankback.data.dto.transaction.TransactionDTO;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.Transaction;
import com.example.bankback.data.repository.AccountRepository;
import com.example.bankback.data.repository.TransactionRepository;
import com.example.bankback.data.dto.transaction.converters.DtoToTransactionConverter;
import com.example.bankback.data.dto.transaction.converters.TransactionToDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import javax.persistence.EntityNotFoundException;
import java.util.List;

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
                .toList();
    }

    @Override
    @Transactional
    public TransactionDTO create(TransactionDTO transactionDTO) {

        Account creditorAccount = accountRepository.findByIBAN(transactionDTO.getCreditor())
                .orElseThrow(() -> new EntityNotFoundException("Account with IBAN " + transactionDTO.getCreditor() + " not found"));


        if (creditorAccount.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account with IBAN: " + transactionDTO.getCreditor());
        }


        LocalDateTime now = LocalDateTime.now();
        transactionDTO.setDateCreated(now);
        transactionDTO.setDateExecuted(now.plusDays(1));


        Transaction transaction = toEntityConverter.apply(transactionDTO);
        transaction = repository.save(transaction);


        creditorAccount.setBalance(creditorAccount.getBalance().subtract(transactionDTO.getAmount()));
        accountRepository.save(creditorAccount);

        return toDtoConverter.apply(transaction);
    }


}

