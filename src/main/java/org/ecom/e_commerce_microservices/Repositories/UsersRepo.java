package org.ecom.e_commerce_microservices.Repositories;

import org.ecom.e_commerce_microservices.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users,Long> {
}
