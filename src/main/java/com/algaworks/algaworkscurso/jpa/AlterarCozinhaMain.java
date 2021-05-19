package com.algaworks.algaworkscurso.jpa;

import com.algaworks.algaworkscurso.AlgaworkscursoApplication;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class AlterarCozinhaMain {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworkscursoApplication.class)
                .web(WebApplicationType.NONE).run(args);

        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);
        cozinha.setNome("Brasileira");

        Cozinha cozinhaPersisitido = cadastroCozinha.save(cozinha);

        System.out.printf("%d - %s\n", cozinhaPersisitido.getId(), cozinhaPersisitido.getNome());
    }
}
