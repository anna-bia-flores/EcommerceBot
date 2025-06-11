package br.edu.ibmec.cloud.Ecommerce.useCases.creditCards;

import br.edu.ibmec.cloud.Ecommerce.repositories.CreditCardRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteCreditCardUseCase {

    private final CreditCardRepository repository;

    public DeleteCreditCardUseCase(CreditCardRepository repository) {
        this.repository = repository;
    }

    public void execute(String cardId) {
        if (!repository.existsById(cardId)) {
            throw new IllegalArgumentException("Credit card not found");
        }

        repository.deleteById(cardId);
    }
}
