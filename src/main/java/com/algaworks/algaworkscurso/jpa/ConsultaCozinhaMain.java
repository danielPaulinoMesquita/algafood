package com.algaworks.algaworkscurso.jpa;

import com.algaworks.algaworkscurso.AlgaworkscursoApplication;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworkscursoApplication.class)
                .web(WebApplicationType.NONE).run(args);

        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

        List<Cozinha> cozinhas = cadastroCozinha.listar();

        for(Cozinha cozinha : cozinhas){
            System.out.println(cozinha.getNome());
        }
    }
}
