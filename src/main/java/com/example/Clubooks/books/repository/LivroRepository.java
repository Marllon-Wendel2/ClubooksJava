package com.example.Clubooks.books.repository;

import com.example.Clubooks.books.model.Avaliacao;
import com.example.Clubooks.books.model.Livro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LivroRepository extends MongoRepository<Livro, String> {

    // Método para encontrar livro pelo título e capa
    Livro findByTitleAndCapa(String title, String capa);

    List<Livro>findByAutorIgnoreCase(String autor);

    boolean existsByTitleAndCapa(String title, String capa);


}
