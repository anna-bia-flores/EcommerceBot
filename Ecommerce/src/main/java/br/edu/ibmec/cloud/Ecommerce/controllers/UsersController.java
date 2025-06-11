package br.edu.ibmec.cloud.Ecommerce.controllers;

import br.edu.ibmec.cloud.Ecommerce.dtos.users.*;
import br.edu.ibmec.cloud.Ecommerce.useCases.users.*;
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

    private final LoginUserUseCase loginUserUseCase;
    public UsersController(
            CreateUserUseCase createUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            UpdateUserUseCase updateUserUseCase,
            ListUsersUseCase listUsersUseCase,
            LoginUserUseCase loginUserUseCase
    ) {
        this.loginUserUseCase = loginUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.listUsersUseCase = listUsersUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto dto) {
        UserDto user = loginUserUseCase.execute(dto);
        return ResponseEntity.ok(user);
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
    public ResponseEntity<List<UserSummaryDto>> listAll() {
        List<UserSummaryDto> users = listUsersUseCase.execute();
        return ResponseEntity.ok(users);
    }
}
