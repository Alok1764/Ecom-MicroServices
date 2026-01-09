package org.ecom.e_commerce_microservices.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Request.CartItemRequest;
import org.ecom.e_commerce_microservices.Entities.CartItem;
import org.ecom.e_commerce_microservices.Entities.Product;
import org.ecom.e_commerce_microservices.Entities.Users;
import org.ecom.e_commerce_microservices.Repositories.CartItemRepo;
import org.ecom.e_commerce_microservices.Repositories.ProductRepo;
import org.ecom.e_commerce_microservices.Repositories.UsersRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;
    private final UsersRepo userRepo;

    public boolean addToCart(String userId, CartItemRequest request) {
        Product product = productRepo.findById(request.getProductId())
                .orElse(null);
        if (product == null) return false;

        if (product.getStockQuantity() < request.getQuantity())
            return false;

        Users users=userRepo.findById(Long.valueOf(userId))
                .orElse(null);

        if(users==null) return false;

        CartItem existingCartItem=cartItemRepo.findByUserAndProduct(users,product);

        if(existingCartItem!=null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
            existingCartItem.setPrice(existingCartItem.getPrice().add(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));
            cartItemRepo.save(existingCartItem);
        }
        else{
            CartItem cartItem = new CartItem();
            cartItem.setUser(users);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepo.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Product product = productRepo.findById(productId)
                .orElse(null);
        Users users = userRepo.findById(Long.valueOf(userId))
                .orElse(null);

        if(product!=null && users!=null){
           cartItemRepo.deleteByUserAndProduct(users,product);
           return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return userRepo.findById(Long.valueOf(userId))
                .map(cartItemRepo::findByUser)
                .orElseGet(List::of);
    }

    public void clearCart(String userId) {
        userRepo.findById(Long.valueOf(userId)).ifPresent(
                cartItemRepo::deleteByUser);
    }
}
