package com.amanat.backend;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

//mongodb integration
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email); // <-- return Optional<User>
}
