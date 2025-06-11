package br.edu.ibmec.cloud.Ecommerce.useCases.users;

import br.edu.ibmec.cloud.Ecommerce.dtos.users.UserSummaryDto;
import br.edu.ibmec.cloud.Ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUsersUseCase {

    private final UserRepository repository;

    public ListUsersUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public List<UserSummaryDto> execute() {
        return repository.findAll().stream()
                .map(UserSummaryDto::fromEntity)
                .collect(Collectors.toList());
    }
}
