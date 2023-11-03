package com.example.bankback.data.dto;

import com.example.bankback.data.dto.UserDTO;
import com.example.bankback.data.dto.converters.DtoToUserConverter;
import com.example.bankback.data.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DtoToUserConverterIntegrationTest {

    @Autowired
    private DtoToUserConverter dtoToUserConverter;

    @Test
    void testConvertUserDtoToUser() {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Test");
        userDTO.setLastName("Testovic");
        userDTO.setEmail("test@example.com");
        userDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        userDTO.setPassport("1234 567890");
        userDTO.setPhoneNumber("123-456-7890");

        User user = dtoToUserConverter.apply(userDTO);

        assertThat(user.getId()).isEqualTo(userDTO.getId());
        assertThat(user.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
        assertThat(user.getBirthDate()).isEqualTo(userDTO.getBirthDate());
        assertThat(user.getPassport()).isEqualTo(userDTO.getPassport());
        assertThat(user.getPhoneNumber()).isEqualTo(userDTO.getPhoneNumber());
    }
}

