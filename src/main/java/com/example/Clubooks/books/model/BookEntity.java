package com.example.Clubooks.books.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "book_id")
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String autor;

    private String titulo;

    private byte[] capa;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sinopse> sinopses;

}
