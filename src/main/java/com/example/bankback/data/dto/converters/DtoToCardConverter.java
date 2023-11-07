package com.example.bankback.data.dto.converters;

import com.example.bankback.data.dto.CardDTO;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.Card;
import com.example.bankback.data.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class DtoToCardConverter implements Function<CardDTO, Card> {

    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Autowired
    DtoToCardConverter(ModelMapper modelMapper,AccountRepository accountRepository)
    {
        this.modelMapper = modelMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public Card apply(CardDTO cardDTO) {
        Card card = modelMapper.map(cardDTO, Card.class);
        if (cardDTO.getAccountId() != null) {
            Optional<Account> accountOptional = accountRepository.findById(cardDTO.getAccountId());
            accountOptional.ifPresent(card::setAccount);
        }
        return card;
    }
}
