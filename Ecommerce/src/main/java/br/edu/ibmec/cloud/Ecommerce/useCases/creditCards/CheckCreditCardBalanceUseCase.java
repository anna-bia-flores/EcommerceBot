package br.edu.ibmec.cloud.Ecommerce.useCases.creditCards;

import br.edu.ibmec.cloud.Ecommerce.dtos.card.CheckCreditCardBalanceRequestDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.card.CheckCreditCardBalanceResponseDto;
import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import br.edu.ibmec.cloud.Ecommerce.repositories.CreditCardRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckCreditCardBalanceUseCase {

    private final CreditCardRepository repository;

    public CheckCreditCardBalanceUseCase(CreditCardRepository repository) {
        this.repository = repository;
    }

    public CheckCreditCardBalanceResponseDto execute(CheckCreditCardBalanceRequestDto dto) {
        CreditCard card = repository.findById(dto.getCardId())
            .orElseThrow(() -> new IllegalArgumentException("Credit card not found"));

        boolean sufficient = card.hasSufficientBalance(dto.getAmount());
        return new CheckCreditCardBalanceResponseDto(card.getId(), sufficient);
    }
}
