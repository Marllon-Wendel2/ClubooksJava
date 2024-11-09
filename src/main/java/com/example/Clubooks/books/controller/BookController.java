package com.example.Clubooks.books.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @GetMapping("/{id}")
    public void getBooks(@PathVariable Long id){
        
    }
}
