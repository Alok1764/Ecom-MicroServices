package org.ecom.e_commerce_microservices.Services;

import lombok.RequiredArgsConstructor;
import org.ecom.e_commerce_microservices.DTO.Response.OrderItemDTO;
import org.ecom.e_commerce_microservices.DTO.Response.OrderResponse;
import org.ecom.e_commerce_microservices.Entities.CartItem;
import org.ecom.e_commerce_microservices.Entities.Order;
import org.ecom.e_commerce_microservices.Entities.OrderItem;
import org.ecom.e_commerce_microservices.Enums.OrderStatus;
import org.ecom.e_commerce_microservices.Repositories.OrderRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
//    private  final UsersRepo usersRepo;
    private final OrderRepo orderRepo;

    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

//        Users user=usersRepo.findById(Long.valueOf(userId))
//                .orElse(null);
//        if(user==null) return Optional.empty();

        BigDecimal totalPrice=cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Order order=Order.builder()
                .userId(userId)
                .status(OrderStatus.CONFIRMED)
                .totalAmount(totalPrice)
                .build();

        List<OrderItem> orderItems=cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .productId(cartItem.getProductId())
                        .price(cartItem.getPrice())
                        .quantity(cartItem.getQuantity())
                        .order(order)
                        .build()
                )
                .toList();
        order.setOrderItems(orderItems);
        Order savedOrder=orderRepo.save(order);

        cartService.clearCart(userId);
        return Optional.of(mapToOrderResponse(savedOrder));

    }
    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .items(
                        order.getOrderItems().stream()
                                .map(orderItem -> OrderItemDTO.builder()
                                        .id(orderItem.getId())
                                        .productId(orderItem.getProductId())
                                        .quantity(orderItem.getQuantity())
                                        .price(orderItem.getPrice())
                                        .subTotal(orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity())))
                                        .build()
                                )
                                .toList()
                )
                .createdAt(order.getCreatedAt())
                .build();
    }
}
