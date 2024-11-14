package com.example.Clubooks.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

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
