package org.ecom.e_commerce_microservices.Services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Request.CartItemRequest;
import org.ecom.e_commerce_microservices.Entities.CartItem;
import org.ecom.e_commerce_microservices.Repositories.CartItemRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

//    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;
//    private final UsersRepo userRepo;

    public boolean addToCart(String userId, CartItemRequest request) {
//        Product product = productRepo.findById(request.getProductId())
//                .orElse(null);
//        if (product == null) return false;
//
//        if (product.getStockQuantity() < request.getQuantity())
//            return false;
//
//        Users users=userRepo.findById(Long.valueOf(userId))
//                .orElse(null);
//
//        if(users==null) return false;

        CartItem existingCartItem=cartItemRepo.findByUserIdAndProductId(userId,request.getProductId());

        if(existingCartItem!=null){
            existingCartItem.setQuantity(existingCartItem.getQuantity()+ request.getQuantity());
//            existingCartItem.setPrice(existingCartItem.getPrice().add(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity()))));
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(existingCartItem);
        }
        else{
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(request.getProductId());
            cartItem.setQuantity(request.getQuantity());
//            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepo.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, String productId) {

        CartItem cartItem = cartItemRepo.findByUserIdAndProductId(userId, productId);


        if (cartItem != null){
            cartItemRepo.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        return cartItemRepo.findByUserId(userId);
    }

    public void clearCart(String userId) {
         cartItemRepo.deleteByUserId(userId);
    }
}
