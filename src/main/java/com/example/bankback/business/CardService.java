package com.example.bankback.business;

import com.example.bankback.data.dto.card.CardBlockDTO;
import com.example.bankback.data.dto.card.CardDTO;
import com.example.bankback.data.dto.card.CardReadDTO;
import com.example.bankback.data.dto.card.converters.CardToDtoConverter;
import com.example.bankback.data.dto.card.converters.CardToReadDtoConverter;
import com.example.bankback.data.dto.card.converters.DtoToCardConverter;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.repository.CardRepository;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService extends  AbstractCrudService<CardDTO,Long, Card, CardRepository>  {

    private final CardToReadDtoConverter toReadEntityConverter;

    protected CardService(CardToReadDtoConverter toReadEntityConverter,
                          CardRepository repository,
                          DtoToCardConverter toEntityConverter,
                         CardToDtoConverter toDtoConverter) {
        super(repository, toEntityConverter, toDtoConverter);
        this.toReadEntityConverter = toReadEntityConverter;
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


    public List<CardReadDTO> findAllByAccountId(Long accountId) {
        List<Card> cards = repository.findByAccountId(accountId);
        return cards.stream()
                .map(toReadEntityConverter)
                .collect(Collectors.toList());
    }

    public  CardReadDTO findByAccountIdAndCardId(Long cardId, Long accountId)
    {
        Optional<Card> card = repository.findByIdAndAccountId(cardId,accountId);
        if (card.isEmpty()) {
            throw new EntityNotFoundException("Card not found with id " + cardId + " for account " + accountId);
        }
        return  toReadEntityConverter.apply(card.get());
    }

    @Transactional
    public CardReadDTO updateCardStatus(Long accountId, Long cardId, CardBlockDTO cardBlockDTO) {
        Card card = repository.findByIdAndAccountId(cardId, accountId)
                .orElseThrow(() -> new EntityNotFoundException("Card not found with id " + cardId + " for account " + accountId));

        card.setBlocked(cardBlockDTO.getBlocked());
        card.setDateLocked(card.isBlocked() ? LocalDate.now() : null);

        repository.save(card);
        return toReadEntityConverter.apply(card);
    }
}
