package com.example.Clubooks.books.repository;

import com.example.Clubooks.books.model.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LivroRepository extends MongoRepository<Livro, String> {

    // Método para encontrar livro pelo título e capa
    Livro findByTitleAndCapa(String title, String capa);
}
