package com.example.bankback.controller;

import com.example.bankback.business.UserService;
import com.example.bankback.data.dto.UserDTO;
import com.example.bankback.data.dto.converters.user.DtoToUserConverter;
import com.example.bankback.data.dto.converters.user.UserToDtoConverter;
import com.example.bankback.data.entity.User;
import com.example.bankback.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.function.Function;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractCrudController<User, UserDTO,UserDTO, Long, UserRepository> {

    private final UserService userService;

    @Autowired
    protected UserController(
            UserService userService,
            UserRepository repository,
            UserToDtoConverter toDtoConverter,
            DtoToUserConverter toEntityConverter) {
        super(repository, toDtoConverter, toDtoConverter, toEntityConverter);
        this.userService = userService;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO dto) {
        UserDTO createdUser = userService.create(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto, @PathVariable Long id) {
        try {
            userService.update(dto, id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(dto);
    }

}

