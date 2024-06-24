package com.elociny.literalura;

import com.elociny.literalura.main.Principal;
import com.elociny.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "com.elociny.literalura.model")
@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Autowired
	private AutorRepository repository;

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(repository);
		principal.exibirMenuInicial();
	}
}
