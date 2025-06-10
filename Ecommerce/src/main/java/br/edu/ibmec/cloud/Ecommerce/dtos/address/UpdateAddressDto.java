package br.edu.ibmec.cloud.Ecommerce.dtos.address;

import br.edu.ibmec.cloud.Ecommerce.models.UserAddress;
import lombok.Data;

@Data
public class UpdateAddressDto {

    private String street;
    private String number;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public void applyTo(UserAddress address) {
        if (street != null) address.setStreet(street);
        if (number != null) address.setNumber(number);
        if (city != null) address.setCity(city);
        if (state != null) address.setState(state);
        if (postalCode != null) address.setPostalCode(postalCode);
        if (country != null) address.setCountry(country);
    }
}
