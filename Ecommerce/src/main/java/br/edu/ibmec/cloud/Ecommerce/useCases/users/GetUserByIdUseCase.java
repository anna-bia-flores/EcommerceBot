package br.edu.ibmec.cloud.Ecommerce.useCases.users;

import br.edu.ibmec.cloud.Ecommerce.dtos.users.UserDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdUseCase {

    private final UserRepository repository;

    public GetUserByIdUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto execute(String id) {
        User user = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return UserDto.fromEntity(user);
    }
}
