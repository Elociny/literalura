package com.elociny.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("name") String autor,
        @JsonAlias("languages") String idiomas,
        @JsonAlias("download_count") String downloads
) {
}