package br.edu.ibmec.cloud.Ecommerce.dtos.address;

import br.edu.ibmec.cloud.Ecommerce.models.User;
import br.edu.ibmec.cloud.Ecommerce.models.UserAddress;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateAddressDto {

    @NotBlank private String street;
    @NotBlank private String number;
    @NotBlank private String city;
    @NotBlank private String state;
    @NotBlank private String postalCode;
    @NotBlank private String country;

    public UserAddress toEntity(User user) {
        UserAddress address = new UserAddress();
        address.setId(UUID.randomUUID().toString());
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setCity(this.city);
        address.setState(this.state);
        address.setPostalCode(this.postalCode);
        address.setCountry(this.country);
        address.setUser(user);
        return address;
    }
}
