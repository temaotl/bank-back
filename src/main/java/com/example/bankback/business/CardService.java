package com.example.bankback.business;

import com.example.bankback.data.dto.card.CardDTO;
import com.example.bankback.data.dto.card.converters.CardToDtoConverter;
import com.example.bankback.data.dto.card.converters.DtoToCardConverter;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.repository.CardRepository;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

@Service
public class CardService extends  AbstractCrudService<CardDTO,Long, Card, CardRepository>  {


    protected CardService(CardRepository repository,
                          DtoToCardConverter toEntityConverter,
                         CardToDtoConverter toDtoConverter) {
        super(repository, toEntityConverter, toDtoConverter);
    }

    @Override
    @Transactional
    public void update(CardDTO cardDTO, Long id) {
        Card existingCard = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found with id " + id));

        Card updatedCard = toEntityConverter.apply(cardDTO);
        updatedCard.setId(existingCard.getId());
        repository.save(updatedCard);
    }
}
