package com.example.Clubooks.books.service;

import com.example.Clubooks.books.dto.AutoresDTO;
import com.example.Clubooks.books.dto.BookDTO;
import com.example.Clubooks.books.model.Livro;
import com.example.Clubooks.books.repository.LivroRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //exclui todos os livros
    public void deletartudo() {
        livroRepository.deleteAll();
    }

    //conta todos os livros
    public long contarlivros() {
        return livroRepository.count();
    }

    public boolean livroExistente(String title, String capa) {
        // Verifica se o livro com o título e a capa existe
        if (livroRepository.existsByTitleAndCapa(title, capa)) {
            return true;

        } else {
            return false;
        }

    }

    public BookDTO consumirapis(String query) {

        ConverterDados conversor = new ConverterDados();
        ConsumirAPI consumir = new ConsumirAPI();

        var json = consumir.obterdados(query);
        System.out.println("Resposta JSON da API: " + json);


        var dados = conversor.obterdados(json, BookDTO.class);
        System.out.println("Dados convertidos para BookDTO: " + dados);


        return dados;
    }

    public List<AutoresDTO> procurarAutor(String autor) {
        List<Livro> livros = livroRepository.findByAutorIgnoreCase(autor);
        return livros.stream().map(livro -> new AutoresDTO(livro.getAutor(), livro.getTitle(), livro.getSinopse(), livro.getData())).collect(Collectors.toList()); // Acesso correto ao método getDataCriacao())).collect(Collectors.toList());
    
    }



}
