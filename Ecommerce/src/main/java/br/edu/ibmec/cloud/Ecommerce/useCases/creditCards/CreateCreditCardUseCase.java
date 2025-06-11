package br.edu.ibmec.cloud.Ecommerce.useCases.creditCards;

import br.edu.ibmec.cloud.Ecommerce.dtos.card.CreateCreditCardDto;
import br.edu.ibmec.cloud.Ecommerce.models.CreditCard;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.repositories.CreditCardRepository;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCreditCardUseCase {

    private final UserRepository userRepository;
    private final CreditCardRepository cardRepository;

    public CreateCreditCardUseCase(UserRepository userRepository, CreditCardRepository cardRepository) {
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    public void execute(String userId, CreateCreditCardDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        CreditCard card = dto.toEntity(user);
        cardRepository.save(card);
    }
}
