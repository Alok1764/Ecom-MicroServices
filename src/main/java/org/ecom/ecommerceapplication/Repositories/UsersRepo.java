package org.ecom.ecommerceapplication.Repositories;

import org.ecom.ecommerceapplication.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<Users,Long> {
}
