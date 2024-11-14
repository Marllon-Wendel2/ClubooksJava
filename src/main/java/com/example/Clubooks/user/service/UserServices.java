package com.example.Clubooks.user.service;

import com.example.Clubooks.user.dto.UserDTO;
import com.example.Clubooks.user.model.User;
import com.example.Clubooks.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
public class UserServices {
    private static final int TOKEN_LENGTH = 8;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User createUser(UserDTO userDTO) {
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

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }

    public User updateUser(String id, User updatedUser) {
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()) {
            User existingUser = user.get();

            if(updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getHashPassword() != null) {
                existingUser.setHashPassword(updatedUser.getHashPassword());
            }
            if (updatedUser.getEmailConfirmed() != null) {
                existingUser.setEmailConfirmed(updatedUser.getEmailConfirmed());
            }
            if (updatedUser.getTokenConfirmation() != null) {
                existingUser.setTokenConfirmation(updatedUser.getTokenConfirmation());
            }

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("Usuário com o ID " + id + " não encontrado");
        }
    }

    public boolean deleteUser(String id) {
        if(userRepository.existsById(id)){
        userRepository.deleteById(id);
        return true;
        } else {
            return false;
        }

    }
}
