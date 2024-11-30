package com.example.Clubooks.books.model;

public class Avaliacao {

    private String idUsuario;
    private Double nota;

    public Avaliacao() {
    }

    public Avaliacao(String idUsuario, Double nota) {
        this.idUsuario = idUsuario;
        this.nota = nota;
    }


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }
}
