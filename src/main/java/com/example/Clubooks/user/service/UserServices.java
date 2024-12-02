package com.example.Clubooks.user.service;

import com.example.Clubooks.logs.dtos.LogsDTO;
import com.example.Clubooks.logs.service.LogsService;
import com.example.Clubooks.user.dto.UserDTO;
import com.example.Clubooks.user.model.Authority;
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
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    private static final int TOKEN_LENGTH = 8;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogsService logsService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public String createUser(UserDTO userDTO) {
        if(userRepository.existsByEmail(userDTO.email())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        if(userRepository.existsByUsername(userDTO.username())) {
            throw new IllegalArgumentException("Username já cadastrado");
        }

        User newUser = new User();
        newUser.setUsername(userDTO.username());
        newUser.setHashPassword(passwordEncoder.encode(userDTO.password()));
        newUser.setEmail(userDTO.email());
        newUser.setTokenConfirmation(generateToken());
        userRepository.save(newUser);

        LogsDTO register = new LogsDTO("Criado no momento", "User criado", "users", "", userDTO.username());
        logsService.createLogs(register);

        return "Usuário criado com sucesso";
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
                LogsDTO register = new LogsDTO(existingUser.getId(), "Username alterado", "users", existingUser.getUsername(), updatedUser.getUsername());
                logsService.createLogs(register);

                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                LogsDTO register = new LogsDTO(existingUser.getId(), "E-mail alterado", "users", existingUser.getEmail(), updatedUser.getEmail());
                logsService.createLogs(register);

                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getHashPassword() != null) {
                LogsDTO register = new LogsDTO(existingUser.getId(), "Password alterado", "users", existingUser.getHashPassword(), updatedUser.getHashPassword());
                logsService.createLogs(register);

                existingUser.setHashPassword(updatedUser.getHashPassword());
            }
            if (updatedUser.getEmailConfirmed() != null
                    && !updatedUser.getEmailConfirmed().equals(existingUser.getEmailConfirmed())
            ) {
                LogsDTO register = new LogsDTO(existingUser.getId(), "Confirmação do e-mail", "users", "", "");
                logsService.createLogs(register);

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

    public String beAuthor(String id) {

        String newRole = "ROLE_AUTHOR";

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));


        List<Authority> currentRoles = user.getRoles();
        if (currentRoles.stream().anyMatch(role -> role.getAuthority().equals(newRole))) {
            throw new IllegalStateException("O usuário já possui a role " + newRole);
        }

        currentRoles.add( new Authority(newRole));

        user.setRoles(currentRoles);
        userRepository.save(user);

        return "Agora " + user.getUsername() + " é autor";
    }

    public boolean deleteUser(String id) {
        if(userRepository.existsById(id)){
        userRepository.deleteById(id);
        return true;
        } else {
            return false;
        }

    }

    public void confirmedEmail(String token) {
        User user = userRepository.findByTokenConfirmation(token);

        if(user != null) {
            user.setEmailConfirmed(true);
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Token de confirmação inválido ou usuário não encontrado.");
        }
    }
}
