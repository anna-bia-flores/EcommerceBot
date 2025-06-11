package br.edu.ibmec.cloud.Ecommerce.models;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryAddress {

    private String street;
    private String number;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public String getFullAddress() {
        return String.format(
                "%s, %s - %s, %s - %s, %s",
                street,
                number,
                city,
                state,
                postalCode,
                country
        );
    }

    public boolean isValid() {
        return street != null && !street.isBlank()
                && number != null && !number.isBlank()
                && city != null && !city.isBlank()
                && state != null && !state.isBlank()
                && postalCode != null && !postalCode.isBlank()
                && country != null && !country.isBlank();
    }
}
