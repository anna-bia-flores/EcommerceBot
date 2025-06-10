package br.edu.ibmec.cloud.Ecommerce.useCases.creditCards;

import br.edu.ibmec.cloud.Ecommerce.dtos.card.ValidateCreditCardDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.card.ValidateCreditCardRequestDto;
import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import br.edu.ibmec.cloud.Ecommerce.repositories.CreditCardRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidateCreditCardUseCase {

    private final CreditCardRepository repository;

    public ValidateCreditCardUseCase(CreditCardRepository repository) {
        this.repository = repository;
    }

    public ValidateCreditCardDto execute(ValidateCreditCardRequestDto dto) {
        CreditCard card = repository.findById(dto.getCardId())
            .orElseThrow(() -> new IllegalArgumentException("Credit card not found"));

        boolean isValid = card.isValid() && card.getCvv().equals(dto.getCvv());
        return new ValidateCreditCardDto(dto.getCardId(), isValid);
    }
}
