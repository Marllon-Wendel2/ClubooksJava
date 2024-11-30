package com.example.Clubooks.books.service;

import com.example.Clubooks.books.dto.AutoresDTO;
import com.example.Clubooks.books.dto.BookDTO;
import com.example.Clubooks.books.exceptions.exceptionPersonalizada;
import com.example.Clubooks.books.model.Avaliacao;
import com.example.Clubooks.books.model.Livro;
import com.example.Clubooks.books.repository.LivroRepository;
import com.example.Clubooks.user.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

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
        if (livroRepository.findAll().isEmpty()) {
            throw new exceptionPersonalizada("Esta vazio ou ja foi deletado todos");
        }
        livroRepository.deleteAll();
    }

    //conta todos os livros
    public long contarlivros() {
        if (livroRepository.count() == 0) {
            throw new exceptionPersonalizada("Esta vazio ou foi deletado todos");
        }
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

    public void avaliarLivro(Double UsuarioAvaliacao, String idUsuario, String idLivro) {

        var usuario = userRepository.findById(idUsuario);
        var livroOpt = livroRepository.findById(idLivro);

        if (UsuarioAvaliacao < 0 || UsuarioAvaliacao > 5 ) {
            throw new exceptionPersonalizada("O numero não pode ser maior que 5 ou menor que 0");
        }

        if(usuario.isEmpty()) {
            throw new exceptionPersonalizada("Usuario não encontrado " + idUsuario);
        }

        if(livroOpt.isEmpty()) {
            throw new exceptionPersonalizada("Livro não encontrado " + idLivro);
        }

        Livro livrinho = livroOpt.get();

        boolean usuarioJaAvaliou = livrinho.getAvaliacao().stream()
                .anyMatch(avaliacao -> avaliacao.getIdUsuario().equals(idUsuario));

        if (usuarioJaAvaliou) {
            throw new exceptionPersonalizada("usuario ja avaliou");
        }

        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setIdUsuario(idUsuario);
        novaAvaliacao.setNota(UsuarioAvaliacao);
        livrinho.getAvaliacao().add(novaAvaliacao);

        livroRepository.save(livrinho);
    }

    public Double mostrarAvaliacaoGeral(String idLivro){
        var livroOpt = livroRepository.findById(idLivro);
        if (livroOpt.isEmpty()) {
            throw new exceptionPersonalizada("o livro não encontrado");
        }
        Livro livro = livroOpt.get();

        if (livro.getAvaliacao().isEmpty()) {
            throw new exceptionPersonalizada("o livro não tem nenhuma avaliação");
        }


        Double mediaAvaliacao = livro.getAvaliacao().stream().mapToDouble(avaliacao -> avaliacao.getNota()).average().orElse(0.0);

        if (mediaAvaliacao == 0.0) {
            throw new exceptionPersonalizada("Este livro não possui avaliação");
        }
        return mediaAvaliacao;

    }


}
