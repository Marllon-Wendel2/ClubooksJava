package com.example.Clubooks.user.dtos;

import com.example.Clubooks.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public record UserDTO(
        @NotBlank(message = "Informe username")
        String username,

        @NotBlank(message = "Informe hashSenha")
        String password,

        @NotBlank(message = "Informe um e-mail")
        @Email(message = "E-mail inválido")
        String email
) {
}
