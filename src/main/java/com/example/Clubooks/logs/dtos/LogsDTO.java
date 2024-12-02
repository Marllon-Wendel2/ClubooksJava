package com.example.Clubooks.logs.dtos;

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
        String oldUser,

        @NotNull
        String attUser
) {
}
