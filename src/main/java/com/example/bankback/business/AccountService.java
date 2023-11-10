package com.example.bankback.business;

import com.example.bankback.data.dto.account.AccountDTO;
import com.example.bankback.data.dto.account.AccountReadDTO;
import com.example.bankback.data.dto.account.converters.AccountToDtoConverter;
import com.example.bankback.data.dto.account.converters.AccountToReadDtoConverter;
import com.example.bankback.data.dto.account.converters.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AccountService extends AbstractCrudService<AccountDTO, Long, Account, AccountRepository> {
    private final AccountToReadDtoConverter toReadEntityConverter;

    @Autowired
    public AccountService(AccountToReadDtoConverter accountToReadDtoConverter,AccountRepository repository,
                          DtoToAccountConverter dtoToAccountConverter,
                          AccountToDtoConverter accountToDtoConverter) {
        super(repository, dtoToAccountConverter, accountToDtoConverter);
        this.toReadEntityConverter = accountToReadDtoConverter;
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
                .map(toReadEntityConverter)
                .toList();
    }

    @Override
    @Transactional
    public AccountDTO create(AccountDTO accountDTO) {
        if (repository.existsByIBAN(accountDTO.getIBAN())) {
            throw new IllegalStateException("Account with IBAN " + accountDTO.getIBAN() + " already exists");
        }
        return super.create(accountDTO);
    }


}
