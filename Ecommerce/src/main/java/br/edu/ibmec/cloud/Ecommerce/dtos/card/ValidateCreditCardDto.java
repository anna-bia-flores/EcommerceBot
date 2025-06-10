package br.edu.ibmec.cloud.Ecommerce.dtos.card;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidateCreditCardDto {
    private String cardId;
    private boolean valid;
}
