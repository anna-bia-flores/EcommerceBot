package br.edu.ibmec.cloud.Ecommerce.dtos.address;

import br.edu.ibmec.cloud.Ecommerce.models.UserAddress;
import lombok.Data;

@Data
public class UserAddressDto {
    private String id;
    private String street;
    private String number;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public static UserAddressDto fromEntity(UserAddress address) {
        UserAddressDto dto = new UserAddressDto();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setNumber(address.getNumber());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPostalCode(address.getPostalCode());
        dto.setCountry(address.getCountry());
        return dto;
    }
}
