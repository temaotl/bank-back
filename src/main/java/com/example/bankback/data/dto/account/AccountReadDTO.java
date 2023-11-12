package com.example.bankback.data.dto.account;

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

    @NotBlank(message = "IBAN is mandatory")
    private String iban;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @PositiveOrZero(message = "Balance cannot be negative")
    private BigDecimal balance;

    @NotBlank(message = "Currency is mandatory")
    private String currency;
}
