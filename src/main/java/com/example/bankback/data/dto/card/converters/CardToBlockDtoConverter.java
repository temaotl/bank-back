package com.example.bankback.data.dto.card.converters;

import com.example.bankback.data.dto.card.CardBlockDTO;
import com.example.bankback.data.entity.Card;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.function.Function;

@Component
public class CardToBlockDtoConverter implements Function<Card, CardBlockDTO> {
    private final ModelMapper modelMapper;

    @Autowired
    public CardToBlockDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CardBlockDTO apply(Card card) {
        return modelMapper.map(card, CardBlockDTO.class);
    }
}
