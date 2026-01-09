package org.ecom.e_commerce_microservices.Entities;

import org.ecom.e_commerce_microservices.DTO.Response.AddressDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;

    @OneToOne
    @JoinColumn(name="user_id",nullable = false)
    private Users user;

    private String state;
    private String country;
    private String zipcode;


}
