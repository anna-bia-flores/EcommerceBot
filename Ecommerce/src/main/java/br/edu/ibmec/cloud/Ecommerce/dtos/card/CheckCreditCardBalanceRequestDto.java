package br.edu.ibmec.cloud.Ecommerce.dtos.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckCreditCardBalanceRequestDto {
    @NotBlank
    private String cardId;

    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
}
