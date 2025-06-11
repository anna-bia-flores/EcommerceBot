package br.edu.ibmec.cloud.Ecommerce.useCases.users;

import br.edu.ibmec.cloud.Ecommerce.dtos.users.LoginDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.users.UserDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class LoginUserUseCase {

    private final UserRepository repository;

    public LoginUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public UserDto execute(LoginDto dto) {
        User user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Senha inválida");
        }

        return UserDto.fromEntity(user);
    }
}
