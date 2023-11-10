package com.example.bankback.business;

import com.example.bankback.data.dto.user.UserDTO;
import com.example.bankback.data.entity.User;
import com.example.bankback.data.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class UserService extends AbstractCrudService<UserDTO, Long, User, UserRepository> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;



    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        super(userRepository, dto -> modelMapper.map(dto, User.class), entity -> modelMapper.map(entity, UserDTO.class));
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    @Transactional
    public void update(UserDTO dto, Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + id));
        User updatedUser = toEntityConverter.apply(dto);
        updatedUser.setId(existingUser.getId());
        modelMapper.map(updatedUser, existingUser);
        userRepository.save(existingUser);
    }


}
