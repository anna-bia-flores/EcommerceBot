package br.edu.ibmec.cloud.Ecommerce.dtos.card;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ValidateCreditCardRequestDto {
    @NotBlank
    private String cardId;

    @NotBlank
    private String cvv;
}
