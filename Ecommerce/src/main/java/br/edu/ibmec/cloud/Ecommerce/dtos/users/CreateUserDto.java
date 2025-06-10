package br.edu.ibmec.cloud.Ecommerce.dtos.users;

import br.edu.ibmec.cloud.Ecommerce.dtos.address.CreateAddressDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class CreateUserDto {

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull
    private List<CreateAddressDto> addresses; // Novo campo

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    public User toEntity() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setBalance(java.math.BigDecimal.ZERO);

        if (this.addresses != null && !this.addresses.isEmpty()) {
            user.setAddresses(this.addresses.stream()
                    .map(dto -> dto.toEntity(user))
                    .collect(Collectors.toList()));
        }

        return user;
    }
}
