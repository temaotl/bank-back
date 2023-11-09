package com.example.bankback.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountReadDTO {

    private Long id;
    private String IBAN;
    private String name;
    private BigDecimal balance;
    private String currency;
}
