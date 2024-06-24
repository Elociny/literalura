package com.elociny.literalura.services;

import com.elociny.literalura.model.DadosAutor;
import com.elociny.literalura.model.DadosLivro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ObterDados {
    private DadosAutor autor;
    private DadosLivro livro;
    private String nomeAutor;
    private String titulo;
    private String anoDeNascimento;
    private String anoDeFalecimento;
    private String dowloads;
    private String idioma;
    private String url;

    public ObterDados(String url) {
        this.url = url;
    }

    public DadosAutor obterDadosAutor() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new URL(url));

            // Access the "results" array
            JsonNode resultsNode = rootNode.get("results");
            if (resultsNode.size() == 0) {
                System.out.println("Não foi possível encontrar um livro com esse título no nosso banco de dados");
                throw new IllegalArgumentException("Não foi possível encontrar um livro com esse título no nosso banco de dados");

            }

// Loop through each result object (book)
            if (resultsNode != null) {
                for (JsonNode resultNode : resultsNode) {

                    titulo = resultNode.get("title").asText();
                    dowloads = resultNode.get("download_count").asText();

                    JsonNode idiomasNode = resultNode.get("languages");
                    // Access the "language" array inside the current result
                    for (JsonNode idiomaNode : idiomasNode) {
                        idioma = idiomaNode.asText();
                    }

                    // Access the "authors" array inside the current result
                    JsonNode authorsNode = resultNode.get("authors");

                    // Loop through each author object
                    for (JsonNode authorNode : authorsNode) {
                        // Extract author information using JsonNode methods
                        nomeAutor = authorNode.get("name").asText();
                        anoDeNascimento = authorNode.get("birth_year").asText();
                        anoDeFalecimento = authorNode.get("death_year").asText();
                    }
                }
            }


            // Create a DadosAutor object with extracted data
            return autor = new DadosAutor(nomeAutor, anoDeNascimento, anoDeFalecimento);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public DadosLivro obterDadosLivro() {
        // Create a DadosLivro object with extracted data
        return livro = new DadosLivro(titulo, nomeAutor, idioma, dowloads);
    }
}
