package br.edu.ibmec.cloud.Ecommerce.controllers;

import br.edu.ibmec.cloud.Ecommerce.dtos.users.CreateUserDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.users.UpdateUserDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.users.UserDto;
import br.edu.ibmec.cloud.Ecommerce.useCases.users.CreateUserUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.users.GetUserByIdUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.users.ListUsersUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.users.UpdateUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final ListUsersUseCase listUsersUseCase;

    public UsersController(
        CreateUserUseCase createUserUseCase,
        GetUserByIdUseCase getUserByIdUseCase,
        UpdateUserUseCase updateUserUseCase,
        ListUsersUseCase listUsersUseCase
    ) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody CreateUserDto dto) {
        UserDto user = createUserUseCase.execute(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable String id) {
        UserDto user = getUserByIdUseCase.execute(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
        @PathVariable String id,
        @Valid @RequestBody UpdateUserDto dto
    ) {
        UserDto user = updateUserUseCase.execute(id, dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listAll() {
        List<UserDto> users = listUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }
}
