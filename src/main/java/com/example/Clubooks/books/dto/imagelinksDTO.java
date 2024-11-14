package com.example.Clubooks.books.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record imagelinksDTO(

        @JsonAlias("thumbnail") String capa
) {
}
