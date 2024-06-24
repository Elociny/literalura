package com.elociny.literalura.main;

import com.elociny.literalura.model.*;
import com.elociny.literalura.repository.AutorRepository;
import com.elociny.literalura.services.ObterDados;
import com.elociny.literalura.model.Autor;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://gutendex.com/books?search=";
    private List<Livro> livrosList = new ArrayList<>();
    private AutorRepository repository;


    public Principal(AutorRepository repository) {
        this.repository = repository;
    }

    public void exibirMenuInicial() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    ******************** Literalura ********************
                    Escolha o número da sua opção:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Obter os top 10 mais baixados
                    7 - Buscar pelo nome do autor
                                        
                    0 - Sair
                    ****************************************************
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();


            switch (opcao) {
                case 1:
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegitrados();
                    break;
                case 4:
                    listarAutoresVivosPeloAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 6:
                    obterTop10Baixados();
                    break;
                case 7:
                    buscarAutor();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("""
                            ****************************************
                                         Opção Inválida
                            ****************************************
                            """);
            }
        }
    }

    private void buscarLivroPorTitulo() {
        System.out.println("Insira o nome do livro desejado: ");
        var tituloLivro = leitura.nextLine();
        var endereco = ENDERECO + tituloLivro.replace(" ", "+");
        ObterDados extrairDados = new ObterDados(endereco);
        DadosAutor dadosAutor = extrairDados.obterDadosAutor();
        Autor autor = new Autor(dadosAutor);
        DadosLivro dadosLivro = extrairDados.obterDadosLivro();
        Livro livro = new Livro(dadosLivro);
        livrosList.add(livro);
        autor.setLivros(livrosList);
        repository.save(autor);
        System.out.println(livro);
    }

    private void listarLivrosRegistrados() {
        List<Livro> lista = repository.listarLivroRegistrados();
        lista.forEach(System.out::println);
    }

    private void listarAutoresRegitrados() {
        List<Autor> listaAutores = repository.listarAutoresRegitrados();
        listaAutores.forEach(System.out::println);
    }

    private void listarAutoresVivosPeloAno() {
        System.out.println("Digite o ano desejado para listarmos os autores vivos deste ano:");
        int ano = leitura.nextInt();
        leitura.nextLine();
        List<Autor> lista = repository.listarAutoresVivosPeloAno(ano);
        lista.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("Insira o idioma para realizar a busca: \n" +
                "pt - português \n" +
                "en - inglês \n" +
                "fr - francês \n" +
                "es - espanhol \n"
        );
        String idioma = leitura.nextLine();

        List<Livro> listaPorIdioma = repository.listarLivrosPorIdioma(Idiomas.fromString(idioma));
        if (listaPorIdioma.size() == 0) {
            System.out.println("Não existem livros registrados neste idioma");
        } else {
            listaPorIdioma.forEach(System.out::println);
        }

    }

    private void obterTop10Baixados() {
        List<Livro> top10 = repository.obterTop10Baixados();
        top10.forEach(System.out::println);
    }

    private void buscarAutor() {
        System.out.println("Digite o nome do autor para busca: ");
        String nomeAutor = leitura.nextLine().toLowerCase();

        List<Autor> listaAutores = repository.listarAutoresRegitrados();

        Optional<Autor> autorEncontrado = listaAutores.stream()
                .filter(a -> a.getNome().toLowerCase().contains(nomeAutor))
                .findFirst();

        if (autorEncontrado.isPresent()) {
            Autor autor = autorEncontrado.get();
            System.out.println(autor);
        } else {
            System.out.println("Autor não encontrado.");
        }
    }
}