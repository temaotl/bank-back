package com.example.bankback.data.dto.converters;

import com.example.bankback.data.dto.AccountDTO;
import com.example.bankback.data.entity.Account;
import com.example.bankback.data.entity.User;
import com.example.bankback.data.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class DtoToAccountConverter implements Function<AccountDTO, Account> {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public DtoToAccountConverter(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Account apply(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        if (accountDTO.getUserId() != null) {
            Optional<User> userOptional = userRepository.findById(accountDTO.getUserId());
            userOptional.ifPresent(account::setUser);
        }
        return account;
    }
}

