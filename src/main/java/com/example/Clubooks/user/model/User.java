package com.example.Clubooks.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String hashPassword;

    @Indexed(unique = true)
    private String email;

    private Boolean emailConfirmed = false;
    private String tokenConfirmation;
    private LocalDateTime createdAt = LocalDateTime.now();

    public User() {
    }
}