package com.example.bankback.controller;


import com.example.bankback.business.AccountService;
import com.example.bankback.data.dto.AccountDTO;
import com.example.bankback.data.dto.converters.AccountToDtoConverter;
import com.example.bankback.data.dto.converters.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController extends AbstractCrudController<Account, AccountDTO, Long, AccountRepository> {

    private final AccountService accountService;
    private final DtoToAccountConverter dtoToAccountConverter;
    private final AccountToDtoConverter accountToDtoConverter;

    @Autowired
    public AccountController(AccountService accountService,
                             AccountRepository accountRepository,
                             AccountToDtoConverter accountToDtoConverter,
                             DtoToAccountConverter dtoToAccountConverter) {
        super(accountRepository, accountToDtoConverter, dtoToAccountConverter);
        this.accountService = accountService;
        this.dtoToAccountConverter = dtoToAccountConverter;
        this.accountToDtoConverter = accountToDtoConverter;
    }

    @Override
    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        AccountDTO createdAccount = accountService.create(dto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@Valid @RequestBody AccountDTO dto, @PathVariable Long id) {
        try {
            accountService.update(dto, id);
            Account updatedAccount = dtoToAccountConverter.apply(dto);
            return ResponseEntity.ok(accountToDtoConverter.apply(updatedAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@RequestParam Long userId) {
        List<AccountDTO> accounts = accountService.findAllByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

}

