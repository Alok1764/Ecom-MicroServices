package org.ecom.e_commerce_microservices.Repositories;

import org.ecom.e_commerce_microservices.Entities.CartItem;
//import org.ecom.e_commerce_microservices.Entities.Product;
//import org.ecom.e_commerce_microservices.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    CartItem findByUserIdAndProductId(String userId,String productId);

    void deleteByUserIdAndProductId(String userId,String productId);

    List<CartItem> findByUserId(String userId);

    void deleteByUserId(String userId);
}
