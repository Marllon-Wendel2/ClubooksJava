    package com.example.Clubooks.books.dto;

    import com.fasterxml.jackson.annotation.JsonAlias;
    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

    import java.util.List;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record BookDTO(
    @JsonAlias("title") List<String> titulo,
    @JsonAlias("authors")String autores,
    @JsonAlias("pageCount")String numerodepaginas,
    @JsonAlias("publishedDate")String datadepublicacao,
    @JsonAlias("description")String descricao

    ) {
    }
