package com.example.Clubooks.books.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record AutoresDTO(
        String autor,
        String title,
        String sinopse,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Sao Paulo")
        Instant dataCriacao

) {
}
