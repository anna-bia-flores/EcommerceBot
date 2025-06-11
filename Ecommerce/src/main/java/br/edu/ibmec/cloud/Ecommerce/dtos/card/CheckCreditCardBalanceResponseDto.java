package br.edu.ibmec.cloud.Ecommerce.dtos.card;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckCreditCardBalanceResponseDto {
    private String cardId;
    private boolean sufficient;
}
