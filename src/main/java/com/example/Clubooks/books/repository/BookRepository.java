package com.example.Clubooks.books.repository;


import com.example.Clubooks.books.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
