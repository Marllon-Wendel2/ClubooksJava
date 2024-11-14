package com.example.Clubooks.books.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemDTO(
        VolumeInfoDTO volumeInfo
) {}
