package br.edu.ibmec.cloud.Ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_addresses")
@Data
public class UserAddress {

    @Id
    private String id;

    private String street;
    private String number;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public DeliveryAddress toDeliveryAddress() {
        DeliveryAddress delivery = new DeliveryAddress();
        delivery.setStreet(this.street);
        delivery.setNumber(this.number);
        delivery.setCity(this.city);
        delivery.setState(this.state);
        delivery.setPostalCode(this.postalCode);
        delivery.setCountry(this.country);
        return delivery;
    }
}
