package br.edu.ibmec.cloud.Ecommerce.dtos.users;

import br.edu.ibmec.cloud.Ecommerce.models.User;
import lombok.Data;

@Data
public class UserSummaryDto {
    private String id;
    private String name;
    private String email;

    public static UserSummaryDto fromEntity(User user) {
        UserSummaryDto dto = new UserSummaryDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
