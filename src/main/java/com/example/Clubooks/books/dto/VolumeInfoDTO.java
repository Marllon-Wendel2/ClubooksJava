package com.example.Clubooks.books.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VolumeInfoDTO(
        @JsonAlias("title") String titulo,
        @JsonAlias("description") String descricao,
        @JsonAlias("previewLink") String previa,
        @JsonAlias("pageCount") Integer numeroPaginas,
        @JsonAlias("imageLinks") imagelinksDTO imageLinks,
        @JsonAlias("publishedDate") String dataPublicacao,
        @JsonAlias("authors") List<String> autores
        ) {}
