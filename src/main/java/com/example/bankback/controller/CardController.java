package com.example.bankback.controller;



import com.example.bankback.business.CardService;
import com.example.bankback.data.dto.card.CardDTO;
import com.example.bankback.data.dto.card.CardReadDTO;
import com.example.bankback.data.dto.card.converters.CardToDtoConverter;
import com.example.bankback.data.dto.card.converters.CardToReadDtoConverter;
import com.example.bankback.data.dto.card.converters.DtoToCardConverter;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
@RequestMapping("/cards")

public class CardController extends AbstractCrudController<Card, CardDTO, CardReadDTO, Long, CardRepository> {

    private final  CardService service;

    @Autowired
    protected CardController(CardService service,
                             CardRepository repository,
                             CardToDtoConverter toDtoConverter,
                             CardToReadDtoConverter toReadDtoConverter,
                             DtoToCardConverter toEntityConverter) {
        super(repository, toDtoConverter, toReadDtoConverter, toEntityConverter);
        this.service = service;
    }


    @Override
    @PostMapping
    public ResponseEntity<CardDTO> create(@Valid @RequestBody CardDTO dto) {
        CardDTO createdCard = service.create(dto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> update(@Valid @RequestBody CardDTO dto, @PathVariable Long id) {
        try {
            service.update(dto, id);
            Card updatedCard = toEntityConverter.apply(dto);
            return ResponseEntity.ok(toDtoConverter.apply(updatedCard));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

