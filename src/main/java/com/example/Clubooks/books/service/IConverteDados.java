package com.example.Clubooks.books.service;

public interface IConverteDados {
    <T> T obterdados(String json, Class<T> classe);
}