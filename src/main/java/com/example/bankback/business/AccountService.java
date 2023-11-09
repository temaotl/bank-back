package com.example.bankback.business;

import com.example.bankback.data.dto.AccountDTO;
import com.example.bankback.data.dto.AccountReadDTO;
import com.example.bankback.data.dto.converters.account.AccountToDtoConverter;
import com.example.bankback.data.dto.converters.account.AccountToReadDtoConverter;
import com.example.bankback.data.dto.converters.account.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class AccountService extends AbstractCrudService<AccountDTO, Long, Account, AccountRepository> {
    private final AccountToReadDtoConverter accountToDtoConverter;

    @Autowired
    public AccountService(AccountToReadDtoConverter accountToReadDtoConverter,AccountRepository repository,
                          DtoToAccountConverter dtoToAccountConverter,
                          AccountToDtoConverter accountToDtoConverter) {
        super(repository, dtoToAccountConverter, accountToDtoConverter);
        this.accountToDtoConverter = accountToReadDtoConverter;
    }

    @Override
    @Transactional
    public void update(AccountDTO accountDTO, Long id) {
        Account existingAccount = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + id));
        Account updatedAccount = toEntityConverter.apply(accountDTO);
        updatedAccount.setId(existingAccount.getId());
        repository.save(updatedAccount);
    }

    public List<AccountReadDTO> findAllByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(accountToDtoConverter)
                .collect(Collectors.toList());
    }

}
