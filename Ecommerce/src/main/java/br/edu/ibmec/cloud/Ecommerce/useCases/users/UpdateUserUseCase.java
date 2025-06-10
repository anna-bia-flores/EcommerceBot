package br.edu.ibmec.cloud.Ecommerce.useCases.users;

import br.edu.ibmec.cloud.Ecommerce.dtos.users.UpdateUserDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.users.UserDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserUseCase {

    private final UserRepository repository;

    public UpdateUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserDto execute(String id, UpdateUserDto dto) {
        User user = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        dto.applyTo(user);
        User updated = repository.save(user);

        return UserDto.fromEntity(updated);
    }
}
