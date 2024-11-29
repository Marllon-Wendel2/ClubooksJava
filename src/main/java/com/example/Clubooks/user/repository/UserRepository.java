package com.example.Clubooks.user.repository;

import com.example.Clubooks.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String userName);
    User findByTokenConfirmation(String token);
    Optional<User> findByEmail(String email);

    UserDetails findByUsername(String username);
}
