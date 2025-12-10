package org.ecom.ecommerceapplication.Repositories;

import org.ecom.ecommerceapplication.Entities.CartItem;
import org.ecom.ecommerceapplication.Entities.Product;
import org.ecom.ecommerceapplication.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {
    CartItem findByUsersAndProduct(Users users, Product product);

    void deleteByUsersAndProducts(Users users, Product product);

    List<CartItem> findByUser(Users user);

    void deleteByUser(Users user);
}
