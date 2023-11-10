package com.example.bankback.data.dto.card.converters;

import com.example.bankback.data.dto.card.CardReadDTO;
import com.example.bankback.data.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class CardToReadDtoConverter implements Function<Card, CardReadDTO> {
    private final ModelMapper modelMapper;

    @Autowired
    public CardToReadDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CardReadDTO apply(Card card) {
        return modelMapper.map(card, CardReadDTO.class);
    }

}
