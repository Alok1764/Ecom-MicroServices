package org.ecom.ecommerceapplication.Repositories;

import org.ecom.ecommerceapplication.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo  extends JpaRepository<Order,Long> {
}
