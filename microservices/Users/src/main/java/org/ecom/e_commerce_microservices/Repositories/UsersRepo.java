package org.ecom.e_commerce_microservices.Repositories;

import org.ecom.e_commerce_microservices.Entities.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepo extends MongoRepository<Users,String> {
}
