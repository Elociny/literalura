package com.elociny.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idiomas idiomas;
    private Integer downloads;
    @ManyToOne
    private Autor autor;


    public Livro() {
    }

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idiomas = Idiomas.fromString(dadosLivro.idiomas());
        this.downloads = Integer.valueOf(dadosLivro.downloads());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Idiomas getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Idiomas idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return  "******************** Literalura ******************** \n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNome() + "\n" +
                "Idiomas: " + idiomas + "\n" +
                "Número de downloads: " + downloads +
                "\n****************************************************";
    }
}