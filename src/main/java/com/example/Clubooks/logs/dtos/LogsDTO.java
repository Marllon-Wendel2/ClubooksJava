package com.example.Clubooks.logs.dtos;

import com.example.Clubooks.user.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LogsDTO(
        @NotBlank
        String userId,
        @NotNull
        String action,
        @NotNull
        String schema,

        @NotNull
        User oldUser,

        @NotNull
        User attUser
) {
}
