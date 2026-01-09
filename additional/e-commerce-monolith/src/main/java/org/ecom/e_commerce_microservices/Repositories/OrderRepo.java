package org.ecom.e_commerce_microservices.Repositories;

import org.ecom.e_commerce_microservices.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo  extends JpaRepository<Order,Long> {
}
