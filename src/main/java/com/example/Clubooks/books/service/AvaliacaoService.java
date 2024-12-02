package com.example.Clubooks.books.service;

import com.example.Clubooks.books.dto.AutoresAvalicaoDTO;
import com.example.Clubooks.books.dto.RecomendacaoLivroDTO;
import com.example.Clubooks.books.model.Avaliacao;
import com.example.Clubooks.books.repository.LivroRepository;
import com.example.Clubooks.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UserRepository userRepository;


    public List<Avaliacao> obterTodasAsAvaliacoes() {
        return livroRepository.findAll().stream().flatMap(livro -> livro.getAvaliacao().stream()).collect(Collectors.toList());
    }


    public List<RecomendacaoLivroDTO> recomendacaoLivro() {
        return livroRepository.findAll().stream()
                .map(livro -> {
                    // Calcula a média das avaliações do livro
                    Double mediaAvaliacoes = livro.getAvaliacao().stream()
                            .mapToDouble(Avaliacao::getNota)  // Extrai as notas das avaliações
                            .average()                       // Calcula a média
                            .orElse(0.0);                    // Se não houver avaliações, a média será 0.0

                    // Retorna o DTO com título, autor e média das avaliações
                    return new RecomendacaoLivroDTO(mediaAvaliacoes, livro.getAutor(), livro.getTitle());
                })
                // Ordena a lista com base na média das avaliações (decrescente)
                .sorted(Comparator.comparingDouble(RecomendacaoLivroDTO::nota).reversed())  // Ordena pela média das avaliações
                .collect(Collectors.toList());
    }

}
