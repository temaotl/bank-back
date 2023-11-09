package com.example.bankback.controller;


import com.example.bankback.business.TransactionService;
import com.example.bankback.data.dto.TransactionDTO;
import com.example.bankback.data.dto.converters.DtoToTransactionConverter;
import com.example.bankback.data.dto.converters.TransactionToDtoConverter;
import com.example.bankback.data.entity.Transaction;
import com.example.bankback.data.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
@RequestMapping("/transaction")
public class TransactionController extends AbstractCrudController<Transaction, TransactionDTO, Long, TransactionRepository> {

    private  final TransactionService service;

    @Autowired
    protected TransactionController(TransactionService service,
                                    TransactionRepository repository,
                                    TransactionToDtoConverter toDtoConverter,
                                    DtoToTransactionConverter toEntityConverter) {
        super(repository, toDtoConverter, toEntityConverter);
        this.service = service;
    }


    @Override
    @PostMapping
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody TransactionDTO dto) {
        TransactionDTO createdCard = service.create(dto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@Valid @RequestBody TransactionDTO dto, @PathVariable  Long id) {
        try {
            service.update(dto, id);
            Transaction updated = toEntityConverter.apply(dto);
            return ResponseEntity.ok(toDtoConverter.apply(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
