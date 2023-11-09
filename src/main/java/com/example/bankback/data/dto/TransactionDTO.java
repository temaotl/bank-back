package com.example.bankback.data.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Creditor is mandatory")
    @Size(max = 100, message = "Creditor name must be up to 100 characters long")
    private String creditor;

    @NotBlank(message = "Debtor is mandatory")
    @Size(max = 100, message = "Debtor name must be up to 100 characters long")
    private String debtor;

    @NotNull(message = "Transaction creation date is mandatory")
    private LocalDateTime dateCreated;

    private LocalDateTime dateExecuted;

    @NotNull(message = "Currency is mandatory")
    @Size(min = 3, max = 3, message = "Currency must be a valid ISO currency code")
    private String currency;

}
