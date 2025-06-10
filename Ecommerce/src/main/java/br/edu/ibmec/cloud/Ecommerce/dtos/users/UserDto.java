package br.edu.ibmec.cloud.Ecommerce.dtos.users;

import br.edu.ibmec.cloud.Ecommerce.models.User;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}