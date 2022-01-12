package com.algaworks.algaworkscurso;

import com.algaworks.algaworkscurso.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgaworkscursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworkscursoApplication.class, args);
	}

}
