package com.example.bankback.business;

import com.example.bankback.data.dto.AccountDTO;
import com.example.bankback.data.dto.converters.AccountToDtoConverter;
import com.example.bankback.data.dto.converters.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class AccountService extends AbstractCrudService<AccountDTO, Long, Account, AccountRepository> {

    @Autowired
    public AccountService(AccountRepository repository,
                          DtoToAccountConverter dtoToAccountConverter,
                          AccountToDtoConverter accountToDtoConverter) {
        super(repository, dtoToAccountConverter, accountToDtoConverter);
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
}
