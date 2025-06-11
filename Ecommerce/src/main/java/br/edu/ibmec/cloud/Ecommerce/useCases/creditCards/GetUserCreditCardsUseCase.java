package br.edu.ibmec.cloud.Ecommerce.useCases.creditCards;

import br.edu.ibmec.cloud.Ecommerce.dtos.card.UserCreditCardDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetUserCreditCardsUseCase {

    private final UserRepository userRepository;

    public GetUserCreditCardsUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserCreditCardDto> execute(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getCreditCards().stream()
            .map(UserCreditCardDto::fromEntity)
            .collect(Collectors.toList());
    }
}
