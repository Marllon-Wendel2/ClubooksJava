package com.example.Clubooks.books.controller;


import com.example.Clubooks.books.model.Conteudo;
import com.example.Clubooks.books.model.Livro;
import com.example.Clubooks.books.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Endpoint para salvar ou atualizar um livro
    @PostMapping
    public ResponseEntity<Livro> salvarLivro(@RequestBody Livro livro) {
        try {
            Livro livroSalvo = livroService.salvarLivro(livro);
            return new ResponseEntity<>(livroSalvo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        try {
            List<Livro> livros = livroService.listarTodosLivros();
            return new ResponseEntity<>(livros, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obter um livro específico pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> obterLivroPorId(@PathVariable String id) {
        try {
            Livro livro = livroService.obterLivroPorId(id);
            if (livro != null) {
                return new ResponseEntity<>(livro, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para atualizar um livro existente
    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable String id, @RequestBody Livro livro) {
        try {
            Livro livroAtualizado = livroService.atualizarLivro(id, livro);
            if (livroAtualizado != null) {
                return new ResponseEntity<>(livroAtualizado, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint para excluir um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> excluirLivro(@PathVariable String id) {
        try {
            boolean isDeleted = livroService.excluirLivro(id);
            if (isDeleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/deletartudo")
    public ResponseEntity<?> excluirtudo() {
        livroService.deletartudo();
        return ResponseEntity.ok().build();
    }



}

