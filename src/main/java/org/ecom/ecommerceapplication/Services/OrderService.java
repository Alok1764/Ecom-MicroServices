package org.ecom.ecommerceapplication.Services;

import lombok.RequiredArgsConstructor;
import org.ecom.ecommerceapplication.DTO.Response.OrderItemDTO;
import org.ecom.ecommerceapplication.DTO.Response.OrderResponse;
import org.ecom.ecommerceapplication.Entities.CartItem;
import org.ecom.ecommerceapplication.Entities.Order;
import org.ecom.ecommerceapplication.Entities.OrderItem;
import org.ecom.ecommerceapplication.Entities.Users;
import org.ecom.ecommerceapplication.Enum.OrderStatus;
import org.ecom.ecommerceapplication.Repositories.OrderRepo;
import org.ecom.ecommerceapplication.Repositories.UsersRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private  final UsersRepo usersRepo;
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;

    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItems = cartService.getCart(userId);
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }

        Users user=usersRepo.findById(Long.valueOf(userId))
                .orElse(null);
        if(user==null) return Optional.empty();

        BigDecimal totalPrice=cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        Order order=Order.builder()
                .user(user)
                .status(OrderStatus.CONFIRMED)
                .totalAmount(totalPrice)
                .build();

        List<OrderItem> orderItems=cartItems.stream()
                .map(cartItem -> OrderItem.builder()
                        .product(cartItem.getProduct())
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
                                        .productId(orderItem.getProduct().getId())
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
