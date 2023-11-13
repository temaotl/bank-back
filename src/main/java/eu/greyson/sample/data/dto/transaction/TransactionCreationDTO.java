package eu.greyson.sample.data.dto.transaction;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class TransactionCreationDTO {
    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Debtor is mandatory")
    @Size(max = 100, message = "Debtor name must be up to 100 characters long")
    private String debtor;
}
