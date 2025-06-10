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
}
