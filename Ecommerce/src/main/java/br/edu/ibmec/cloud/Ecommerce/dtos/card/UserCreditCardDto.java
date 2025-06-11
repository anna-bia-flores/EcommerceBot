package br.edu.ibmec.cloud.Ecommerce.dtos.card;

import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserCreditCardDto {

    private String id;
    private String last4Digits;
    private BigDecimal balance;
    private String expirationDate;

    public static UserCreditCardDto fromEntity(CreditCard card) {
        UserCreditCardDto dto = new UserCreditCardDto();
        dto.setId(card.getId());
        dto.setBalance(card.getBalance());

        String number = card.getCardNumber();
        dto.setLast4Digits(number != null && number.length() >= 4
                ? number.substring(number.length() - 4)
                : "****");

        dto.setExpirationDate(card.getExpirationDate());

        return dto;
    }
}
