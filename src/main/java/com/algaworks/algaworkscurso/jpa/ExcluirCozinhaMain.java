package com.algaworks.algaworkscurso.jpa;

import com.algaworks.algaworkscurso.AlgaworkscursoApplication;
import com.algaworks.algaworkscurso.domain.model.Cozinha;
import com.algaworks.algaworkscurso.domain.service.CadastroCozinhaService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ExcluirCozinhaMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworkscursoApplication.class)
                .web(WebApplicationType.NONE).run(args);

        CadastroCozinhaService cadastroCozinhaService = applicationContext.getBean(CadastroCozinhaService.class);

        Cozinha cozinha = new Cozinha();
        cozinha.setId(1L);

        cadastroCozinhaService.excluir(cozinha.getId());
//        List<Cozinha> cozinhas = cadastroCozinha.listar();
//
//        for(Cozinha cozinha : cozinhas){
//            System.out.println(cozinha.getNome());
//        }
    }
}
