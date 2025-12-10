package org.ecom.ecommerceapplication.DTO.Response;

import lombok.Builder;
import lombok.Data;
import org.ecom.ecommerceapplication.Enum.UserRole;

@Data
@Builder
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}

