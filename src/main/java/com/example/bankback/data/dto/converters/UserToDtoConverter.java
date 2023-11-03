package com.example.bankback.data.dto.converters;

import com.example.bankback.data.entity.User;
import com.example.bankback.data.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToDtoConverter implements Function<User, UserDTO> {

    private final ModelMapper modelMapper;

    @Autowired
    public UserToDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public UserDTO apply(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
