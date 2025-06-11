package br.edu.ibmec.cloud.Ecommerce.dtos.card;

import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateCreditCardDto {

    @NotBlank
    private String cardNumber;

    @NotBlank
    @Pattern(regexp = "^\\d{3,4}$", message = "CVV must be 3 or 4 digits")
    private String cvv;


    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Invalid expiration date format. Use MM/yy")
    private String expirationDate;

    private BigDecimal balance = BigDecimal.ZERO;

    public CreditCard toEntity(User user) {
        CreditCard card = new CreditCard();
        card.setId(UUID.randomUUID().toString());
        card.setCardNumber(this.cardNumber);
        card.setCvv(this.cvv);
        card.setExpirationDate(this.expirationDate);
        card.setBalance(balance != null ? balance : BigDecimal.ZERO);
        card.setUser(user);
        return card;
    }
}
