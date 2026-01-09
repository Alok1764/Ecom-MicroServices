package org.ecom.e_commerce_microservices.DTO.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemRequest {
    private Long productId;
    private Integer quantity;
}
