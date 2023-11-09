package com.example.bankback.data.dto.converters.user;


import com.example.bankback.data.entity.User;
import com.example.bankback.data.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DtoToUserConverter implements Function<UserDTO, User> {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoToUserConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User apply(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
