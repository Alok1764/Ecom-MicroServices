package org.ecom.e_commerce_microservices.DTO.Request;

import lombok.Builder;
import lombok.Data;
import org.ecom.e_commerce_microservices.DTO.Response.AddressDTO;

@Data
@Builder
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDTO address;
}
