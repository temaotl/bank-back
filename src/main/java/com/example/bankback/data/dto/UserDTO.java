package com.example.bankback.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @PastOrPresent(message = "Birth date must be in the past or present")
    private LocalDate birthDate;

    @NotBlank(message = "Passport is mandatory")
    private String passport;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @Email(message = "Email must be valid")
    private String email;

    private String address;

}




