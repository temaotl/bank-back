package com.example.bankback.controller;


import com.example.bankback.business.AccountService;
import com.example.bankback.business.CardService;
import com.example.bankback.business.TransactionService;
import com.example.bankback.data.dto.account.AccountDTO;
import com.example.bankback.data.dto.account.AccountReadDTO;
import com.example.bankback.data.dto.card.CardDTO;
import com.example.bankback.data.dto.card.CardReadDTO;
import com.example.bankback.data.dto.transaction.TransactionCreationDTO;
import com.example.bankback.data.dto.transaction.TransactionDTO;
import com.example.bankback.data.dto.account.converters.AccountToDtoConverter;
import com.example.bankback.data.dto.account.converters.AccountToReadDtoConverter;
import com.example.bankback.data.dto.account.converters.DtoToAccountConverter;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController extends AbstractCrudController<Account, AccountDTO,AccountReadDTO, Long, AccountRepository> {

    private final AccountService service;
    private final TransactionService transactionService;
    private final CardService cardService;

    private final ModelMapper modelMapper;

    @Autowired
    protected AccountController(ModelMapper modelMapper,
                                AccountService service,
                                CardService cardService,
                                TransactionService transactionService,
                                AccountRepository repository,
                                AccountToDtoConverter toDtoConverter,
                                AccountToReadDtoConverter toReadDtoConverter,
                                DtoToAccountConverter toEntityConverter) {
        super(repository, toDtoConverter, toReadDtoConverter, toEntityConverter);
        this.service = service;
        this.transactionService = transactionService;
        this.modelMapper =modelMapper;
        this.cardService = cardService;
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
    public ResponseEntity<List<AccountReadDTO>> getAccountsByUserId(@RequestParam Long userId) {
        List<AccountReadDTO> accounts = service.findAllByUserId(userId);
        return ResponseEntity.ok(accounts);
    }


    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getAccountTransactions(@PathVariable Long id) {
        List<TransactionDTO> transactions = transactionService.findAllByAccountId(id);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/{id}/transactions")
    public ResponseEntity<?> createTransaction(@PathVariable Long id, @RequestBody TransactionCreationDTO creationRequest) {
        return service.readById(id).map(account -> {
            TransactionDTO transactionDTO = modelMapper.map(creationRequest, TransactionDTO.class);
            transactionDTO.setCreditor(account.getIBAN());
            transactionDTO.setCurrency(account.getCurrency());

            TransactionDTO createdTransaction = transactionService.create(transactionDTO);
            return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<List<CardReadDTO>> getAccountCards(@PathVariable Long id) {
        if (service.readById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CardReadDTO> cards = cardService.findAllByAccountId(id);
        return ResponseEntity.ok(cards);
    }

}

