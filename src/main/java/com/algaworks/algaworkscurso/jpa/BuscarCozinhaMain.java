package com.algaworks.algaworkscurso.jpa;

import com.algaworks.algaworkscurso.AlgaworkscursoApplication;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;

public class BuscarCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworkscursoApplication.class)
                .web(WebApplicationType.NONE).run(args);

        CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);

        Optional<Cozinha> cozinha = cadastroCozinha.findById(1L);

        System.out.println("Cozinha: "+cozinha.get().getId() +"  Nome:"+cozinha.get().getNome());
    }
}
