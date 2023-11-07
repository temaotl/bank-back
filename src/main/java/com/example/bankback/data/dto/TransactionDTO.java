package com.example.bankback.data.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Transaction date is mandatory")
    private LocalDateTime date;

    @Size(max = 34, message = "Sender account number must be up to 34 characters long")
    private String senderAccountNumber;

    @Size(max = 34, message = "Receiver account number must be up to 34 characters long")
    private String receiverAccountNumber;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotNull(message = "Currency is mandatory")
    @Size(min = 3, max = 3, message = "Currency must be a valid ISO currency code")
    private String currency;

}
