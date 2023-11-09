package com.example.bankback.controller;


import com.example.bankback.business.AccountService;
import com.example.bankback.data.dto.AccountDTO;
import com.example.bankback.data.dto.AccountReadDTO;
import com.example.bankback.data.dto.converters.account.AccountToDtoConverter;
import com.example.bankback.data.dto.converters.account.AccountToReadDtoConverter;
import com.example.bankback.data.dto.converters.account.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/accounts")
public class AccountController extends AbstractCrudController<Account, AccountDTO,AccountReadDTO, Long, AccountRepository> {

    private final AccountService service;

    @Autowired
    protected AccountController(AccountService service,
                                AccountRepository repository,
                                AccountToDtoConverter toDtoConverter,
                                AccountToReadDtoConverter toReadDtoConverter,
                                DtoToAccountConverter toEntityConverter) {
        super(repository, toDtoConverter, toReadDtoConverter, toEntityConverter);
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<AccountDTO> create(@RequestBody AccountDTO dto) {
        AccountDTO createdAccount = service.create(dto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@Valid @RequestBody AccountDTO dto, @PathVariable Long id) {
        try {
            service.update(dto, id);
            Account updatedAccount = toEntityConverter.apply(dto);
            return ResponseEntity.ok(toDtoConverter.apply(updatedAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<AccountDTO>> getAccountsByUserId(@RequestParam Long userId) {
        List<AccountDTO> accounts = service.findAllByUserId(userId);
        return ResponseEntity.ok(accounts);
    }
}

