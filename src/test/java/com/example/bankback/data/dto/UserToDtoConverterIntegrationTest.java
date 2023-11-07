package com.example.bankback.data.dto;

import com.example.bankback.data.dto.converters.UserToDtoConverter;
import com.example.bankback.data.entity.User;
import com.example.bankback.data.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserToDtoConverterIntegrationTest {

    @Autowired
    private UserToDtoConverter userToDtoConverter;

    @Test
    void testConvertUserToUserDTO() {

        User user = new User();
        user.setId(1L);
        user.setFirstName("Test");
        user.setLastName("Testovic");
        user.setEmail("test@example.com");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setPassport("1234 567890");
        user.setPhoneNumber("123-456-7890");

        UserDTO userDTO = userToDtoConverter.apply(user);


        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDTO.getBirthDate()).isEqualTo(user.getBirthDate());
        assertThat(userDTO.getPassport()).isEqualTo(user.getPassport());
        assertThat(userDTO.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
    }
}