package com.example.Clubooks.user;

import com.example.Clubooks.user.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserServices {
    private static final int TOKEN_LENGTH = 8;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User createuser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setHashPassword(passwordEncoder.encode(userDTO.password()));
        newUser.setEmail(userDTO.email());
        newUser.setTokenConfirmation(generateToken());

        return userRepository.save(newUser);
    }

    private String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
}
