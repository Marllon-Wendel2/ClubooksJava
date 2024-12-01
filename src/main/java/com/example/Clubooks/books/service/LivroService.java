package com.example.Clubooks.books.service;

import com.example.Clubooks.books.model.Conteudo;
import com.example.Clubooks.books.model.Livro;
import com.example.Clubooks.books.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // Salva ou atualiza o livro
    public Livro salvarLivro(Livro livro) {
        livro.setData(Instant.now());
        // Verifica se já existe um livro com o mesmo título e capa
        Livro livroExistente = livroRepository.findByTitleAndCapa(livro.getTitle(), livro.getCapa());

        if (livroExistente != null) {
            // Se o livro já existe, não insira novamente, só adicione o conteúdo
            livro.setId(livroExistente.getId());
            livro.setConteudo(livroExistente.getConteudo());
        }

        return livroRepository.save(livro);
    }

    // Lista todos os livros
    public List<Livro> listarTodosLivros() {
        return livroRepository.findAll();
    }

    // Obtém um livro por ID
    public Livro obterLivroPorId(String id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElse(null);
    }

    // Atualiza um livro
    public Livro atualizarLivro(String id, Livro livro) {

        if (livroRepository.existsById(id)) {
            livro.setId(id);
            return livroRepository.save(livro);
        } else {
            return null;
        }
    }

    // Exclui um livro
    public boolean excluirLivro(String id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deletartudo() {
        livroRepository.deleteAll();
    }



}
