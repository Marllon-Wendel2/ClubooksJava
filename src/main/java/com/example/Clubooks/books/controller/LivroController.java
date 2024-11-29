package com.example.Clubooks.books.controller;


import com.example.Clubooks.books.dto.AutoresDTO;
import com.example.Clubooks.books.dto.BookDTO;
import com.example.Clubooks.books.dto.LivroExistenteDTO;
import com.example.Clubooks.books.dto.avaliarDTO;
import com.example.Clubooks.books.exceptions.exceptionPersonalizada;
import com.example.Clubooks.books.model.Conteudo;
import com.example.Clubooks.books.model.Livro;
import com.example.Clubooks.books.service.LivroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@SecurityRequirement(name = "bearer-key")
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

    //excluir o banco por completo
    @DeleteMapping("/deletartudo")
    public ResponseEntity<?> excluirTudo() {
        livroService.deletartudo();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listartotal")
    public ResponseEntity<?> listarNumeroLivro() {
        var a = livroService.contarlivros();
        return ResponseEntity.ok().body(a);
    }

    @PostMapping("/livroexistente")
    public ResponseEntity<LivroExistenteDTO> livroExistente(@RequestBody LivroExistenteDTO dto) {
        boolean existe = livroService.livroExistente(dto.title(), dto.capa());
        if(existe) {
            return ResponseEntity.ok(dto);
        }     
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/procurarlivroexterno/{query}")
    public ResponseEntity<?> procurarLivroExterno(@PathVariable String query) {
        BookDTO json = livroService.consumirapis(query);
        if (json == null || json.items() == null || json.items().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum livro encontrado para a consulta.");
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping("/procurarautor/{autor}")
    public ResponseEntity<List<AutoresDTO>> buscarAutor(@PathVariable String autor) {
        String autorFormatado = autor.replace("_", " ").replace("+", " ");

        List<AutoresDTO> lista = livroService.procurarAutor(autorFormatado);
        if(lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(lista);
        }
    }
    @PostMapping("/avaliar")
    public ResponseEntity<String> avaliar(@RequestBody avaliarDTO avaliar ) {
        try{
            livroService.avaliarLivro(avaliar.avaliacao(), avaliar.idUsuario(), avaliar.idLivro());
            String mensagem = "Avaliação foi registrada com sucesso.";
            return ResponseEntity.ok(mensagem);
        }catch (exceptionPersonalizada e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/avaliacaoMedia/{idLivro}")
    public ResponseEntity<?> avaliacaoMedia(@PathVariable String idLivro) {
        try{
            Double media = livroService.mostrarAvaliacaoGeral(idLivro);
            return ResponseEntity.ok(media);
        }catch (exceptionPersonalizada e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

