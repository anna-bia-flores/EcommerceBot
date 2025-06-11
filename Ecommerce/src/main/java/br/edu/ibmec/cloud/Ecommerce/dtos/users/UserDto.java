package br.edu.ibmec.cloud.Ecommerce.dtos.users;

import br.edu.ibmec.cloud.Ecommerce.dtos.address.UserAddressDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.card.UserCreditCardDto;
import br.edu.ibmec.cloud.Ecommerce.models.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;
    private List<UserAddressDto> addresses;
    private List<UserCreditCardDto> creditCards;

    public static UserDto fromEntity(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        if (user.getAddresses() != null) {
            dto.setAddresses(
                    user.getAddresses().stream()
                            .map(UserAddressDto::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        if (user.getCreditCards() != null) {
            dto.setCreditCards(
                    user.getCreditCards().stream()
                            .map(UserCreditCardDto::fromEntity)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
