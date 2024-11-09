package com.example.Clubooks.books.model;

import jakarta.persistence.*;

@Table(name = "Conteudo")
@Entity
public class Sinopse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String capitulo;

    private String titulo;

    private String texto;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
}
