package com.example.Clubooks.books.service;

import com.example.Clubooks.books.dto.AutoresAvalicaoDTO;
import com.example.Clubooks.books.model.Avaliacao;
import com.example.Clubooks.books.repository.LivroRepository;
import com.example.Clubooks.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
